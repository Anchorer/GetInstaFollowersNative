package me.godap.ins;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "FOLLOWING".
 */
@Entity
public class Following {

    @Id
    private Long userId;
    private String userName;
    private String fullName;
    private String avatarUrl;
    private Boolean isFavorite;
    private Boolean isPrivate;

    @Generated(hash = 1249099706)
    public Following() {
    }

    public Following(Long userId) {
        this.userId = userId;
    }

    @Generated(hash = 1870265573)
    public Following(Long userId, String userName, String fullName, String avatarUrl, Boolean isFavorite, Boolean isPrivate) {
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
