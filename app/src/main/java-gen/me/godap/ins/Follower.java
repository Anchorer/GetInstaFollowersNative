package me.godap.ins;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "FOLLOWER".
 */
@Entity
public class Follower {

    @Id
    private Long userId;
    private String userName;
    private String fullName;
    private String avatarUrl;
    private Boolean isFavorite;
    private Boolean isPrivate;

    @Generated(hash = 1675995425)
    public Follower() {
    }

    public Follower(Long userId) {
        this.userId = userId;
    }

    @Generated(hash = 1211112170)
    public Follower(Long userId, String userName, String fullName, String avatarUrl, Boolean isFavorite, Boolean isPrivate) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.isFavorite = isFavorite;
        this.isPrivate = isPrivate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

}