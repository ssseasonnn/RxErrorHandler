package zlc.season.javademo;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {
    @GET
    Maybe<BaseResponse> getSomeThing(@Url String url);
}


