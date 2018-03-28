package zlc.season.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private val api = RetrofitClient.get().create(Api::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnFoo = findViewById<Button>(R.id.btn_foo)
        btnFoo.setOnClickListener {
            requestSomeThing("http://www.wanandroid.com/tools/mockapi/4037/foo")
        }

        val btnBar = findViewById<Button>(R.id.btn_bar)
        btnBar.setOnClickListener {
            requestSomeThing("http://www.wanandroid.com/tools/mockapi/4037/bar")
        }
    }

    private fun requestSomeThing(url: String) {
        api.getSomeThing(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                }, {
                    Log.e(TAG, "Something wrong", it)
                })
    }
}

