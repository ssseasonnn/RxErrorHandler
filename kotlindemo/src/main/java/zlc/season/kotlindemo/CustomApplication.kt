package zlc.season.kotlindemo

import android.app.Application
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import java.net.ConnectException
import java.net.SocketTimeoutException

class CustomApplication : Application() {
    companion object {
        const val TAG = "CustomApplication"
    }

    var mainHandler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        mainHandler = Handler(mainLooper)

        RxJavaPlugins.setOnObservableSubscribe { _, t2 ->
            ObservableSubscribeHooker(t2)
        }
    }

    inner class ObservableSubscribeHooker<T>(private var actual: Observer<T>) : Observer<T> {
        override fun onComplete() {
            actual.onComplete()
        }

        override fun onSubscribe(d: Disposable) {
            actual.onSubscribe(d)
        }

        override fun onNext(t: T) {
            hookOnNext(t)
            actual.onNext(t)
        }

        private fun hookOnNext(t: T) {
            if (t is BaseResponse) {
                if (t.code == 100) {

                    mainHandler!!.post {
                        Toast.makeText(applicationContext, "登录过期", Toast.LENGTH_SHORT).show()
                    }

                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    throw TokenExpiredException()
                }
            }
        }

        override fun onError(e: Throwable) {
            if (e is ConnectException) {
                Log.e(TAG, "Connect failed: ", e)
                mainHandler!!.post({ Toast.makeText(applicationContext, "无网络连接", Toast.LENGTH_SHORT).show() })
                actual.onError(Exceptions.Offline())
                return
            }

            if (e is SocketTimeoutException) {
                Log.e(TAG, "Time out ", e)
                mainHandler!!.post({ Toast.makeText(applicationContext, "连接超时", Toast.LENGTH_SHORT).show() })
                actual.onError(Exceptions.TimeOut())
                return
            }

            //其余的异常处理...
            actual.onError(e)
        }
    }

    class TokenExpiredException : RuntimeException()
}