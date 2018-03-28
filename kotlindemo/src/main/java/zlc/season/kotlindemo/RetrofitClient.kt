package zlc.season.kotlindemo

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    private const val FAKE_BASE_URL = "http://www.example.com"

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
        return builder.build()
    }

    fun get(baseUrl: String = FAKE_BASE_URL): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}