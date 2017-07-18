package me.godap.ins.component;

import android.util.Log;

import java.io.IOException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowersRequest;
import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowingRequest;
import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramGetUserFollowersResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoggedUser;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsernameResult;
import me.godap.ins.R;
import me.godap.ins.application.GetFollowersApplication;
import me.godap.ins.dao.UserDBManager;
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
    private UserDBManager mUserDBManager;

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

    private InstagramManager() {
        mUserDBManager = UserDBManager.getInstance();
    }

    /**
     * 登录到Instagram
     * @param userName  用户名
     * @param password  密码
     */
    public void login(final String userName, final String password, final ApiCallback<InstagramLoginResult> callback) {
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
                if (callback != null) {
                    callback.onError(e, null);
                }
            }

            @Override
            public void onNext(InstagramLoginResult instagramLoginResult) {
                Log.i(Consts.TAG, "Login Result. Status: " + instagramLoginResult.getStatus() + ", Result: " + instagramLoginResult.toString());
                if (instagramLoginResult.getStatus().equals(getString(R.string.api_status_ok))) {
                    mUser = instagramLoginResult.getLogged_in_user();
                    // 将登录用户信息存到数据库
                    mUserDBManager.saveLoggedUser(mUser);
                    if (callback != null) {
                        callback.onSuccess(instagramLoginResult);
                    }
                } else {
                    if (callback != null) {
                        callback.onError(null, instagramLoginResult.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 通过账号名称获取用户信息
     * @param userName  账户名称
     */
    public void getUserInfo(final String userName, final ApiCallback<InstagramSearchUsernameResult> callback) {
        if (mInstagram == null) {
            if (callback != null) {
                callback.onError(null, getString(R.string.hint_need_login));
            }
            return;
        }
        rx.Observable.create(new Observable.OnSubscribe<InstagramSearchUsernameResult>() {
            @Override
            public void call(Subscriber<? super InstagramSearchUsernameResult> subscriber) {
                try {
                    InstagramSearchUsernameResult result = mInstagram.sendRequest(new InstagramSearchUsernameRequest(userName));
                    subscriber.onNext(result);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<InstagramSearchUsernameResult>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                if (callback != null) {
                    callback.onError(e, null);
                }
            }

            @Override
            public void onNext(InstagramSearchUsernameResult result) {
                Log.i(Consts.TAG, "GetUserInfo Result. Status: " + result.getStatus() + ", Result: " + result.toString());
                if (result.getStatus().equals(getString(R.string.api_status_ok))) {
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                } else {
                    if (callback != null) {
                        callback.onError(null, result.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 获取粉丝列表
     * @param userId    当前用户的ID
     */
    public void getFollowersList(final long userId, final ApiCallback<InstagramGetUserFollowersResult> callback) {
        if (mInstagram == null) {
            if (callback != null) {
                callback.onError(null, getString(R.string.hint_need_login));
            }
            return;
        }
        rx.Observable.create(new Observable.OnSubscribe<InstagramGetUserFollowersResult>() {
            @Override
            public void call(Subscriber<? super InstagramGetUserFollowersResult> subscriber) {
                try {
                    InstagramGetUserFollowersResult result = mInstagram.sendRequest(new InstagramGetUserFollowersRequest(userId, null));
                    subscriber.onNext(result);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<InstagramGetUserFollowersResult>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                if (callback != null) {
                    callback.onError(e, null);
                }
            }

            @Override
            public void onNext(InstagramGetUserFollowersResult result) {
                if (result.getStatus().equals(getString(R.string.api_status_ok))) {
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                } else {
                    if (callback != null) {
                        callback.onError(null, result.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 获取关注列表
     * @param userId    当前用户的ID
     */
    public void getFollowingList(final long userId, final ApiCallback<InstagramGetUserFollowersResult> callback) {
        if (mInstagram == null) {
            if (callback != null) {
                callback.onError(null, getString(R.string.hint_need_login));
            }
            return;
        }
        rx.Observable.create(new Observable.OnSubscribe<InstagramGetUserFollowersResult>() {
            @Override
            public void call(Subscriber<? super InstagramGetUserFollowersResult> subscriber) {
                try {
                    InstagramGetUserFollowersResult result = mInstagram.sendRequest(new InstagramGetUserFollowingRequest(userId, null));
                    subscriber.onNext(result);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<InstagramGetUserFollowersResult>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                if (callback != null) {
                    callback.onError(e, null);
                }
            }

            @Override
            public void onNext(InstagramGetUserFollowersResult result) {
                if (result.getStatus().equals(getString(R.string.api_status_ok))) {
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                } else {
                    if (callback != null) {
                        callback.onError(null, result.getMessage());
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

    /**
     * 获取当前登录用户的用户ID
     */
    public long getUserId() {
        return (mUser == null) ? 0 : mUser.getPk();
    }

    private String getString(int strId) {
        return GetFollowersApplication.mContext.getString(strId);
    }

}
