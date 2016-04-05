package com.scut.easyfe.entity.test;

import com.scut.easyfe.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Date;

/**
 * 系统消息
 * Created by jay on 16/3/29.
 */
public class Message extends BaseEntity {
    private String senderName;
    private Date date;
    private String content;

    public Message(String senderName, Date date, String content) {
        this.senderName = senderName;
        this.date = date;
        this.content = content;
    }

    public static ArrayList<Message> getTestMessage(){
        ArrayList<Message> messages = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            messages.add(new Message("系统消息", new Date(), "我是系统管理员.\n我来收水费喽,麻烦大家开下门."));
        }
        return messages;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
