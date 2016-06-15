package com.scut.easyfe.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮询数据
 * Created by jay on 16/6/15.
 * todo 优化比较相等
 */
public class PollingData {

    private PollingPrivateData mine = new PollingPrivateData();
    private PollingPublicData common = new PollingPublicData();

    public PollingPrivateData getMine() {
        return mine;
    }

    public void setMine(PollingPrivateData mine) {
        this.mine = mine;
    }

    public PollingPublicData getCommon() {
        return common;
    }

    public void setCommon(PollingPublicData common) {
        this.common = common;
    }

    public static class PollingPublicData extends BaseEntity {
        private long vipEvent = 0;
        PollingMessageData message = new PollingMessageData();

        public long getVipEvent() {
            return vipEvent;
        }

        public void setVipEvent(long vipEvent) {
            this.vipEvent = vipEvent;
        }

        public PollingMessageData getMessage() {
            return message;
        }

        public void setMessage(PollingMessageData message) {
            this.message = message;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof PollingPublicData)){
                return false;
            }

            PollingPublicData compareData = (PollingPublicData)o;
            if (getVipEvent() < compareData.getVipEvent()) {
                return false;
            }

            if(!message.equals(compareData.getMessage())){
                return false;
            }

            return true;
        }
    }

    public static class PollingMessageData extends BaseEntity {
        private long all = 0;
        private long parent = 0;
        private long teacher = 0;

        public long getAll() {
            return all;
        }

        public void setAll(long all) {
            this.all = all;
        }

        public long getParent() {
            return parent;
        }

        public void setParent(long parent) {
            this.parent = parent;
        }

        public long getTeacher() {
            return teacher;
        }

        public void setTeacher(long teacher) {
            this.teacher = teacher;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof PollingMessageData)){
                return false;
            }

            PollingMessageData compareData = (PollingMessageData)o;
            if (getAll() < compareData.getAll()) {
                return false;
            }

            if (getParent() < compareData.getParent()) {
                return false;
            }

            if (getTeacher() < compareData.getTeacher()) {
                return false;
            }

            return true;
        }
    }

    public static class PollingPrivateData extends BaseEntity {
        private long wallet = 0;
        private long level = 0;
        private long checkType = 0;
        PollingRewardData reward = new PollingRewardData();
        List<PollingOrderData> order = new ArrayList<>();

        public long getWallet() {
            return wallet;
        }

        public void setWallet(long wallet) {
            this.wallet = wallet;
        }

        public long getLevel() {
            return level;
        }

        public void setLevel(long level) {
            this.level = level;
        }

        public long getCheckType() {
            return checkType;
        }

        public void setCheckType(long checkType) {
            this.checkType = checkType;
        }

        public PollingRewardData getReward() {
            return reward;
        }

        public void setReward(PollingRewardData reward) {
            this.reward = reward;
        }

        public List<PollingOrderData> getOrder() {
            return order;
        }

        public void setOrder(List<PollingOrderData> order) {
            this.order = order;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof PollingPrivateData)){
                return false;
            }

            PollingPrivateData compareData = (PollingPrivateData)o;
            if (getWallet() < compareData.getWallet()) {
                return false;
            }

            if (getCheckType() < compareData.getCheckType()) {
                return false;
            }

            if (getLevel() < compareData.getLevel()) {
                return false;
            }

            if(!getOrder().equals(compareData.getOrder())){
                return false;
            }

            if(!getReward().equals(compareData.getReward())){
                return false;
            }

            return true;
        }
    }

    public static class PollingRewardData extends BaseEntity {
        private long completeCourseParent = 0;
        private long completeCourseTeacher = 0;
        private long inviteParent = 0;
        private long inviteTeacher = 0;
        private long specialOrder = 0;

        public long getCompleteCourseParent() {
            return completeCourseParent;
        }

        public void setCompleteCourseParent(long completeCourseParent) {
            this.completeCourseParent = completeCourseParent;
        }

        public long getInviteParent() {
            return inviteParent;
        }

        public void setInviteParent(long inviteParent) {
            this.inviteParent = inviteParent;
        }

        public long getInviteTeacher() {
            return inviteTeacher;
        }

        public void setInviteTeacher(long inviteTeacher) {
            this.inviteTeacher = inviteTeacher;
        }

        public long getCompleteCourseTeacher() {
            return completeCourseTeacher;
        }

        public void setCompleteCourseTeacher(long completeCourseTeacher) {
            this.completeCourseTeacher = completeCourseTeacher;
        }

        public long getSpecialOrder() {
            return specialOrder;
        }

        public void setSpecialOrder(long specialOrder) {
            this.specialOrder = specialOrder;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof PollingRewardData)){
                return false;
            }

            PollingRewardData compareData = (PollingRewardData)o;

            if (getCompleteCourseParent() < compareData.getCompleteCourseParent()) {
                return false;
            }

            if (getCompleteCourseTeacher() < compareData.getCompleteCourseTeacher()) {
                return false;
            }

            if (getInviteParent() < compareData.getInviteParent()) {
                return false;
            }

            if (getInviteTeacher() < compareData.getInviteTeacher()) {
                return false;
            }

            if (getSpecialOrder() < compareData.getSpecialOrder()) {
                return false;
            }

            return true;
        }
    }

    public static class PollingOrderData extends BaseEntity {
        private String orderId = "";
        private int state = 0;
        private long timestamp = 0;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof PollingOrderData)) {
                return false;
            }

            PollingOrderData compareDate = (PollingOrderData) o;

            if (!orderId.equals(compareDate.getOrderId())) {
                return false;
            }

            if (state != compareDate.getState()) {
                return false;
            }

            if (timestamp < compareDate.timestamp) {
                return false;
            }

            return true;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PollingData)) {
            return false;
        }

        PollingData compareData = (PollingData) other;

        if(! common.equals(compareData.getCommon())){
            return false;
        }

        if(! mine.equals(compareData.getMine())){
            return false;
        }

        return true;
    }
}
