package com.scut.easyfe.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * 系统消息
 * Created by jay on 16/3/29.
 */
public class Message extends BaseEntity {
    private String _id = "";
    private String senderName = "系统消息";
    private Date timestamp;
    private String content;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
