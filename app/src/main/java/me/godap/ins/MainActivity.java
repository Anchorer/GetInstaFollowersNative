package me.godap.ins;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "GetFollowers";

    private EditText mUsernameEt, mPwEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsernameEt = (EditText) findViewById(R.id.username_et);
        mPwEt = (EditText) findViewById(R.id.pw_et);
        findViewById(R.id.login_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn: {
                login();
                break;
            }
        }
    }

    private void login() {
        String userName = mUsernameEt.getText().toString();
        String pw = mPwEt.getText().toString();
        loginToInstagram(userName, pw);
    }

    private void loginToInstagram(final String username, final String password) {
        rx.Observable.create(new Observable.OnSubscribe<InstagramLoginResult>() {
            @Override
            public void call(Subscriber<? super InstagramLoginResult> subscriber) {
                Instagram4Android instagram  = Instagram4Android.builder().username(username).password(password).build();
                instagram.setup();
                try {
                    subscriber.onNext(instagram.login());
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<InstagramLoginResult>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(InstagramLoginResult instagramLoginResult) {
                Log.i(TAG, "Login Result: " + instagramLoginResult.toString());
//                if(instagramLoginResult.getStatus().equals(view.getStringResource(R.string.login_success))) {
//                    view.loginFinished(instagram);
//                } else {
//                    view.loginFailed(instagramLoginResult.getMessage());
//                }
            }
        });
    }

}
