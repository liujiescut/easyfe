package com.scut.easyfe.entity;


/**
 * 钱包类
 * Created by jay on 16/4/10.
 */
public class Wallet extends BaseEntity{
    private String ali = "";
    private String wechat = "";
    private Bank bank = new Bank();
    private float balance;                  // 单位 : 分
    private float withdrawing;              // 单位 : 分
    private float haveWithdraw;             // 单位 : 分

    public String getAli() {
        return ali;
    }

    public void setAli(String ali) {
        this.ali = ali;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getWithdrawing() {
        return withdrawing;
    }

    public void setWithdrawing(float withdrawing) {
        this.withdrawing = withdrawing;
    }

    public float getHaveWithdraw() {
        return haveWithdraw;
    }

    public void setHaveWithdraw(float haveWithdraw) {
        this.haveWithdraw = haveWithdraw;
    }
}
