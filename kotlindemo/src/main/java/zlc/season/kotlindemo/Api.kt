package zlc.season.kotlindemo

import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Url


interface Api {
    @GET
    fun getSomeThing(@Url url: String): Maybe<BaseResponse>
}