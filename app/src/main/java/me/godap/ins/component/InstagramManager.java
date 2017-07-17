package me.godap.ins.component;

import android.util.Log;

import java.io.IOException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoggedUser;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import me.godap.ins.R;
import me.godap.ins.application.GetFollowersApplication;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Anchorer on 2017/7/17.
 */

public class InstagramManager {

    private Instagram4Android mInstagram;
    private InstagramLoggedUser mUser;

    private volatile static InstagramManager sInstance;

    public static InstagramManager getInstance() {
        if (sInstance == null) {
            synchronized (InstagramManager.class) {
                if (sInstance == null) {
                    sInstance = new InstagramManager();
                }
            }
        }
        return sInstance;
    }

    private InstagramManager() {}

    /**
     * 登录到Instagram
     * @param userName  用户名
     * @param password  密码
     */
    public void login(final String userName, final String password, final ApiCallback<InstagramLoginResult> resultApiCallback) {
        rx.Observable.create(new Observable.OnSubscribe<InstagramLoginResult>() {
            @Override
            public void call(Subscriber<? super InstagramLoginResult> subscriber) {
                mInstagram = Instagram4Android.builder().username(userName).password(password).build();
                mInstagram.setup();
                try {
                    subscriber.onNext(mInstagram.login());
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
                if (resultApiCallback != null) {
                    resultApiCallback.onError(e, null);
                }
            }

            @Override
            public void onNext(InstagramLoginResult instagramLoginResult) {
                Log.i(Consts.TAG, "Login Result. Status: " + instagramLoginResult.getStatus() + ", Result: " + instagramLoginResult.toString());
                if (instagramLoginResult.getStatus().equals(GetFollowersApplication.mContext.getString(R.string.api_status_ok))) {
                    mUser = instagramLoginResult.getLogged_in_user();
                    if (resultApiCallback != null) {
                        resultApiCallback.onSuccess(instagramLoginResult);
                    }
                } else {
                    if (resultApiCallback != null) {
                        resultApiCallback.onError(null, instagramLoginResult.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 获取当前登录用户的用户名
     */
    public String getUserName() {
        return (mUser == null) ? null : mUser.getUsername();
    }

    /**
     * 获取当前登录用户的全名
     */
    public String getUserFullName() {
        return (mUser == null) ? null : mUser.getFull_name();
    }

    /**
     * 获取当前登录用户的头像URL
     */
    public String getUserAvatar() {
        return (mUser == null) ? null : mUser.getProfile_pic_url();
    }

}
