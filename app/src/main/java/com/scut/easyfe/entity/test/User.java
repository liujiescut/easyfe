package com.scut.easyfe.entity.test;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.BaseEntity;

/**
 * 用户类
 * Created by jay on 16/4/1.
 */
public class User extends BaseEntity {
    private int userType = Constants.Identifier.USER_PARENT;
    private boolean hasLogin = false;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public boolean isHasLogin() {
        return hasLogin;
    }

    public void setHasLogin(boolean hasLogin) {
        this.hasLogin = hasLogin;
    }
}
