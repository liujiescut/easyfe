package com.scut.easyfe.entity.user;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Address;
import com.scut.easyfe.entity.BaseEntity;
import com.scut.easyfe.utils.ACache;
import com.scut.easyfe.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户基本信息类(家长家教共有的)
 * Created by jay on 16/4/1.
 */
public class User extends BaseEntity{
    //用户token
    private String token = "";

    //用户id
    private String _id = "";

    //用户姓名
    private String name = "";

    //用户性别
    private int gender = Constants.Identifier.FEMALE;

    //用户手机
    private String phone = "";

    //用户密码
    private String password = "";

    //用户类型
    private int type = Constants.Identifier.USER_TEACHER;

    //用户头像
    private String avatar = "";

    //用户地址(包括地址名称,经纬度)
    private Address position = new Address();

    //用户出生日期
    private long birthday = 0l;

    //用户不良记录次数
    private int badRecord = 0;

    //用户邀请的用户的_id列表
    private List<String> invitePerson = new ArrayList<>();

    //家长信息
    private Parent parentMessage = new Parent();

    //家教信息
    private Teacher teacherMessage = new Teacher();

    /**
     * 判断当前用户是否已登陆
     */
    public boolean hasLogin(){
        return token != null && token.length() != 0;
    }

    /**
     * 将用户信息缓存到本地
     */
    public void save2Cache(){
        LogUtils.i("更新缓存用户信息到本地 -> " + this);
        ACache.getInstance().put(Constants.Key.USER_CACHE, this);
    }

    /**
     * 执行默认登录,从缓存中获取用户信息
     */
    public static void doLogout(){
        User user = new User();
        App.setUser(user);
        user.save2Cache();
    }

    /**
     * 执行默认登录,从缓存中获取用户信息
     */
    public static void doLogin(){
        User user = (User) ACache.getInstance().getAsObject(Constants.Key.USER_CACHE);
        if(user != null) App.setUser(user);
        LogUtils.i("登录该用户 -> " + user);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Address getPosition() {
        return position;
    }

    public void setPosition(Address position) {
        this.position = position;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getBadRecord() {
        return badRecord;
    }

    public void setBadRecord(int badRecord) {
        this.badRecord = badRecord;
    }

    public List<String> getInvitePerson() {
        return invitePerson;
    }

    public void setInvitePerson(List<String> invitePerson) {
        this.invitePerson = invitePerson;
    }

    public Parent getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Parent parentMessage) {
        this.parentMessage = parentMessage;
    }

    public Teacher getTeacherMessage() {
        return teacherMessage;
    }

    public void setTeacherMessage(Teacher teacherMessage) {
        this.teacherMessage = teacherMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
