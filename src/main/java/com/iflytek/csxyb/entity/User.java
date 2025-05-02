package com.iflytek.csxyb.entity;

public class User {
    private int userId; // userId; 用户ID
    private String userName; // userName; 用户昵称
    private String loginText; // loginText; 登录账号
    private String password; // password; 密码
    private String phoneNumber; // phoneNumber; 手机号
    private String email; // email; 邮箱
    private String wx; // wx; 微信号
    private UserType type = UserType.regular; // type; 用户类型 {admin, regular}
    private String avatar = "/images/userTx/41bc9d83-68f7-44d7-9e4c-5342d746b649.jpg"; // userTxImg; 用户头像 (图片链接)
    private String remark; // geXin; 个性签名
    private int following; // GuanZhu; 关注数
    private int fans; // Fans; 粉丝数
    private int status;

    public User(String userName, String loginText, String password) {
        this.userName = userName;
        this.loginText = loginText;
        this.password = password;
    }

    public User() {
    }

    public User(String loginText, String password) {
        this.loginText = loginText;
        this.password = password;
    }

    public User(int userId, String userName, String loginText, String password, String phoneNumber, String email, String wx, UserType type, String avatar, String remark, int following, int fans, int status) {
        this.userId = userId;
        this.userName = userName;
        this.loginText = loginText;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.wx = wx;
        this.type = type;
        this.avatar = avatar;
        this.remark = remark;
        this.following = following;
        this.fans = fans;
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", loginText='" + loginText + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", wx='" + wx + '\'' +
                ", type=" + type +
                ", avatar='" + avatar + '\'' +
                ", remark='" + remark + '\'' +
                ", following=" + following +
                ", fans=" + fans +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginText() {
        return loginText;
    }

    public void setLoginText(String loginText) {
        this.loginText = loginText;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
