package com.dreamtea.Boot.Domain;

public class User {
    private String userName;
    private String password;
    private String userNick;
    private int userPlayed;
    private int userWon;
    private int userLost;
    private int userScore;
    private int userLogin;

    public User(String userName, String password, String userNick, int userPlayed, int userWon, int userLost, int userScore, int userLogin) {
        this.userName = userName;
        this.password = password;
        this.userNick = userNick;
        this.userPlayed = userPlayed;
        this.userWon = userWon;
        this.userLost = userLost;
        this.userScore = userScore;
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public int getUserPlayed() {
        return userPlayed;
    }

    public void setUserPlayed(int userPlayed) {
        this.userPlayed = userPlayed;
    }

    public int getUserWon() {
        return userWon;
    }

    public void setUserWon(int userWon) {
        this.userWon = userWon;
    }

    public int getUserLost() {
        return userLost;
    }

    public void setUserLost(int userLost) {
        this.userLost = userLost;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(int userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userNick='" + userNick + '\'' +
                ", userPlayed=" + userPlayed +
                ", userWon=" + userWon +
                ", userLost=" + userLost +
                ", userScore=" + userScore +
                ", userLogin=" + userLogin +
                '}';
    }
}
