package com.scut.easyfe.entity;

import com.scut.easyfe.app.Constants;

/**
 * 评论实体类
 * Created by jay on 16/4/14.
 */
public class Comment extends BaseEntity{
    private String _id = "";
    private String teacher = "";      //老师的Id
    private String content = "";
    private long timestamp = 0;
    private ParentCommentMessage parent = new ParentCommentMessage();


    public ParentCommentMessage getParent() {
        return parent;
    }

    public void setParent(ParentCommentMessage parent) {
        this.parent = parent;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public class ParentCommentMessage extends BaseEntity{
        private String _id = "";
        private String name = "";
        private int gender = Constants.Identifier.FEMALE;

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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
    }
}
