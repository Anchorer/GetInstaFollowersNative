package me.godap.ins.dao;

import me.godap.ins.DaoMaster;
import me.godap.ins.DaoSession;
import me.godap.ins.application.GetFollowersApplication;

/**
 * 数据库管理类
 * Created by Anchorer on 2017/7/18.
 */
public class DBManager {
    private static final String DB_NAME = "GetFollowers.db";
    private volatile static DBManager sInstance;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DBManager() {
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(GetFollowersApplication.mContext, DB_NAME, null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static DBManager getInstance() {
        if (sInstance == null) {
            synchronized (DBManager.class) {
                if (sInstance == null) {
                    sInstance = new DBManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * Get DaoSession
     */
    public DaoSession getDaoSession() {
        return daoSession;
    }

}
