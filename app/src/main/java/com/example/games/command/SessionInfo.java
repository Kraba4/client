package com.example.games.command;

import java.io.Serializable;

public class SessionInfo implements Serializable {
    int sessionId;
    String ownerLogin;
    int maxUsers;
    boolean hasPassword;
    int nUsers;
    String gameName;
    public SessionInfo(int sessionId, String ownerLogin, int maxUsers, boolean hasPassword, int nUsers, String gameName) {
        this.sessionId = sessionId;
        this.ownerLogin = ownerLogin;
        this.maxUsers = maxUsers;
        this.hasPassword = hasPassword;
        this.nUsers = nUsers;
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return "SessionInfo{" +
                "sessionId=" + sessionId +
                ", ownerLogin='" + ownerLogin + '\'' +
                ", maxUsers=" + maxUsers +
                ", hasPassword=" + hasPassword +
                ", nUsers=" + nUsers +
                '}';
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

    public int getnUsers() {
        return nUsers;
    }

    public void setnUsers(int nUsers) {
        this.nUsers = nUsers;
    }
}
