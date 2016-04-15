package com.scut.easyfe.entity;

/**
 * 评论实体类
 * Created by jay on 16/4/14.
 */
public class Comment extends BaseEntity{
    private String parent = "";
    private String teacher = "";
    private String content = "";
    private long timestamp = 0;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
