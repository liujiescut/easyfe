package com.scut.easyfe.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 银行信息类
 */
public class Bank implements Serializable {
    private String name = "";
    private String account = "";

    public JSONObject getBankJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("account", account);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
