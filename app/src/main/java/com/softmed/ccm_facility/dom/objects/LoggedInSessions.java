package com.softmed.ccm_facility.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by issy on 01/05/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Entity
public class LoggedInSessions implements Serializable{

    @NotNull
    @PrimaryKey(autoGenerate = false)
    private String userId;

    private String userName;

    //MD5
    private String userPassword;

    private String facilityUUID;

    private String roleString;

    private long lastLoggedIn;

    private long lastLoggedOut;

    @NotNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NotNull String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public long getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(long lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public long getLastLoggedOut() {
        return lastLoggedOut;
    }

    public void setLastLoggedOut(long lastLoggedOut) {
        this.lastLoggedOut = lastLoggedOut;
    }

    public String getFacilityUUID() {
        return facilityUUID;
    }

    public void setFacilityUUID(String facilityUUID) {
        this.facilityUUID = facilityUUID;
    }

    public String getRoleString() {
        return roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }
}
