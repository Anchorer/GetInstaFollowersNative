package me.godap.ins.dao;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

/**
 * Green DAO Generator
 * Created by Anchorer on 2017/7/18.
 */
public class InsDaoGenerator {

    /**
     * 定义数据库版本
     */
    private static final int mVersion = 1;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(mVersion, "me.godap.ins");
        addLoggedUser(schema);
        addUserDetail(schema);
        addFollower(schema);
        addFollowing(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java-gen");
    }

    /**
     * 增加登录用户信息表
     */
    private static void addLoggedUser(Schema schema) {
        Entity userEntity = schema.addEntity("LoggedUser");
        userEntity.addLongProperty("userId").primaryKey();
        userEntity.addStringProperty("userName");
        userEntity.addStringProperty("fullName");
        userEntity.addStringProperty("avatarUrl");
    }

    /**
     * 增加用户详情表
     */
    private static void addUserDetail(Schema schema) {
        Entity userDetailEntity = schema.addEntity("UserDetail");
        userDetailEntity.addLongProperty("userId").primaryKey();
        userDetailEntity.addStringProperty("userName");
        userDetailEntity.addStringProperty("fullName");
        userDetailEntity.addStringProperty("externalUrl");
        userDetailEntity.addBooleanProperty("isFavorite");
        userDetailEntity.addStringProperty("publicPhoneCountryCode");
        userDetailEntity.addStringProperty("publicPhone");
        userDetailEntity.addStringProperty("publicEmail");
        userDetailEntity.addIntProperty("geoMediaCount");
        userDetailEntity.addIntProperty("userTagsCount");
        userDetailEntity.addStringProperty("avatarUrl");
        userDetailEntity.addStringProperty("addressStreet");
        userDetailEntity.addStringProperty("cityName");
        userDetailEntity.addIntProperty("followerCount");
        userDetailEntity.addIntProperty("followingCount");
    }

    /**
     * 增加粉丝表（关注我的用户）
     */
    private static void addFollower(Schema schema) {
        Entity followerEntity = schema.addEntity("Follower");
        followerEntity.addLongProperty("userId").primaryKey();
        followerEntity.addStringProperty("userName");
        followerEntity.addStringProperty("fullName");
        followerEntity.addStringProperty("avatarUrl");
        followerEntity.addBooleanProperty("isFavorite");
        followerEntity.addBooleanProperty("isPrivate");
    }

    /**
     * 增加关注用户表（我关注的用户）
     */
    private static void addFollowing(Schema schema) {
        Entity followingEntity = schema.addEntity("Following");
        followingEntity.addLongProperty("userId").primaryKey();
        followingEntity.addStringProperty("userName");
        followingEntity.addStringProperty("fullName");
        followingEntity.addStringProperty("avatarUrl");
        followingEntity.addBooleanProperty("isFavorite");
        followingEntity.addBooleanProperty("isPrivate");
    }

}
