package me.godap.ins.dao;

import java.util.List;

import dev.niekirk.com.instagram4android.requests.payload.InstagramLoggedUser;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary;
import me.godap.ins.DaoSession;
import me.godap.ins.Follower;
import me.godap.ins.FollowerDao;
import me.godap.ins.Following;
import me.godap.ins.FollowingDao;
import me.godap.ins.LoggedUser;
import me.godap.ins.LoggedUserDao;
import me.godap.ins.UserDetailDao;

/**
 * 用户相关的数据库管理类
 * Created by Anchorer on 2017/7/18.
 */
public class UserDBManager {
    private LoggedUserDao mLoggedUserDao;
    private UserDetailDao mUserDetailDao;
    private FollowerDao mFollowerDao;
    private FollowingDao mFollowingDao;

    private static UserDBManager sInstance = new UserDBManager();

    private UserDBManager() {
        DaoSession daoSession = DBManager.getInstance().getDaoSession();
        mLoggedUserDao = daoSession.getLoggedUserDao();
        mUserDetailDao = daoSession.getUserDetailDao();
        mFollowerDao = daoSession.getFollowerDao();
        mFollowingDao = daoSession.getFollowingDao();
    }

    public static UserDBManager getInstance() {
        return sInstance;
    }

    /**
     * 存储登录用户信息
     */
    public void saveLoggedUser(InstagramLoggedUser user) {
        if (user != null) {
            LoggedUser loggedUser = new LoggedUser(user.getPk());
            loggedUser.setUserName(user.getUsername());
            loggedUser.setFullName(user.getFull_name());
            loggedUser.setAvatarUrl(user.getProfile_pic_url());
            mLoggedUserDao.insertOrReplace(loggedUser);
        }
    }

    /**
     * 更新粉丝列表数据
     */
    public void updateFollowerList(List<InstagramUserSummary> userSummaryList) {
        if (userSummaryList == null) {
            return;
        }
        mFollowerDao.deleteAll();
        for (InstagramUserSummary instagramUserSummary : userSummaryList) {
            Follower follower = new Follower(instagramUserSummary.getPk());
            follower.setUserName(instagramUserSummary.getUsername());
            follower.setFullName(instagramUserSummary.getFull_name());
            follower.setAvatarUrl(instagramUserSummary.getProfile_pic_url());
            follower.setIsFavorite(instagramUserSummary.is_favorite());
            follower.setIsPrivate(instagramUserSummary.is_private());
            mFollowerDao.insertOrReplace(follower);
        }
    }

    /**
     * 更新关注列表数据
     */
    public void updateFollowingList(List<InstagramUserSummary> userSummaryList) {
        if (userSummaryList == null) {
            return;
        }
        mFollowingDao.deleteAll();
        for (InstagramUserSummary instagramUserSummary : userSummaryList) {
            Following following = new Following(instagramUserSummary.getPk());
            following.setUserName(instagramUserSummary.getUsername());
            following.setFullName(instagramUserSummary.getFull_name());
            following.setAvatarUrl(instagramUserSummary.getProfile_pic_url());
            following.setIsFavorite(instagramUserSummary.is_favorite());
            following.setIsPrivate(instagramUserSummary.is_private());
            mFollowingDao.insertOrReplace(following);
        }
    }

    /**
     * 查询是否已经关注了指定用户
     * @param targetUserId  指定目标用户
     */
    public boolean hasFollowedUser(long targetUserId) {
        List<Following> followings = mFollowingDao.queryBuilder()
                .where(FollowingDao.Properties.UserId.eq(targetUserId)).list();
        return (followings != null) && (followings.size() > 0);
    }

}
