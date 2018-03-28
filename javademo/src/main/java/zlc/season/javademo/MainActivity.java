package zlc.season.javademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Api api = RetrofitClient.get().create(Api.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFoo = findViewById(R.id.btn_foo);
        btnFoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSomeThing("http://www.wanandroid.com/tools/mockapi/4037/foo");
            }
        });

        Button btnBar = findViewById(R.id.btn_bar);
        btnBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSomeThing("http://www.wanandroid.com/tools/mockapi/4037/bar");
            }
        });
    }

    private void requestSomeThing(String url) {
        api.getSomeThing(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        Toast.makeText(MainActivity.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "Something wrong", throwable);
                    }
                });
    }
}
