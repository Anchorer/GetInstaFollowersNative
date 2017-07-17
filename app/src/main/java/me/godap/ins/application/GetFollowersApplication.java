package me.godap.ins.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Anchorer on 2017/7/17.
 */

public class GetFollowersApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
