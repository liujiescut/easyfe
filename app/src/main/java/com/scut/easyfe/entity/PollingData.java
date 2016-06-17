package com.scut.easyfe.entity;

import android.support.annotation.Nullable;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.utils.ACache;
import com.scut.easyfe.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮询数据
 * Created by jay on 16/6/15.
 */
public class PollingData extends BaseEntity {

    //后台返回的Json转化后的字符串
    private String dataString = "";

    private PollingPrivateData mine = new PollingPrivateData();
    private PollingPublicData common = new PollingPublicData();

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    /**
     * 将用户信息缓存到本地
     */
    public void save2Cache(final String phone) {
        final PollingData data = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ACache.getInstance().put(Constants.Key.POLLING_DATA_CACHE + phone, data);
            }
        }).start();
    }

    @Nullable
    public static PollingData getFromCache(String phone){
        PollingData data = (PollingData) ACache.getInstance().getAsObject(Constants.Key.POLLING_DATA_CACHE + phone);
        return data;
    }

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

        /**
         * 会员活动有没有更新
         */
        public boolean isVipNew(PollingPublicData compareData) {
            return getVipEvent() != compareData.getVipEvent();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof PollingPublicData)) {
                return false;
            }

            PollingPublicData compareData = (PollingPublicData) o;
            if (getVipEvent() != compareData.getVipEvent()) {
                return false;
            }

            if (!message.equals(compareData.getMessage())) {
                return false;
            }

            return true;
        }
    }

    public static class PollingMessageData extends BaseEntity {
        private long all = 0;
        private long parent = 0;
        private long teacher = 0;

        public boolean isMessageNew(PollingMessageData compareData) {
            if (all != compareData.getAll()) {
                return true;
            }

            if (App.getUser(false).isParent()) {
                if (parent != compareData.getParent()) {
                    return true;
                }
            } else {
                if (teacher != compareData.getTeacher()) {
                    return true;
                }
            }

            return false;
        }

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
            if (!(o instanceof PollingMessageData)) {
                return false;
            }

            PollingMessageData compareData = (PollingMessageData) o;
            if (getAll() != compareData.getAll()) {
                return false;
            }

            if (getParent() != compareData.getParent()) {
                return false;
            }

            if (getTeacher() != compareData.getTeacher()) {
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

        /**
         * 获取那些状态的订单有更新
         */
        public NewOrderInfo getNewOrderInfo(PollingPrivateData compareData) {
            NewOrderInfo newOrderInfo = new NewOrderInfo();
            for (int i = 0; i < compareData.order.size(); i++) {
                if (!order.contains(compareData.order.get(i))) {
                    newOrderInfo.ids.add(compareData.order.get(i).getOrderId());

                    switch (compareData.order.get(i).getState()) {
                        case Constants.Identifier.ORDER_RESERVATION:
                        case Constants.Identifier.ORDER_MODIFIED_WAIT_CONFIRM:
                            newOrderInfo.state[1] = 1;
                            break;

                        case Constants.Identifier.ORDER_TO_DO:
                            newOrderInfo.state[2] = 1;
                            break;

                        case Constants.Identifier.ORDER_COMPLETED:
                            newOrderInfo.state[3] = 1;
                            break;

                        case Constants.Identifier.ORDER_INVALID:
                            newOrderInfo.state[4] = 1;

                        default:
                            break;
                    }

                    newOrderInfo.state[0] = 1;
                }
            }

            return newOrderInfo;
        }

        public boolean isOrderNew(PollingPrivateData compareData) {
            return !order.containsAll(compareData.order);
        }

        public boolean isWalletNew(PollingPrivateData compareData) {
            return wallet != compareData.getWallet();
        }

        public boolean isLevelNew(PollingPrivateData compareData) {
            return level != compareData.getLevel();
        }

        public boolean isCheckTypeNew(PollingPrivateData compareData) {
            return checkType != compareData.getCheckType();
        }

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
            if (!(o instanceof PollingPrivateData)) {
                return false;
            }

            PollingPrivateData compareData = (PollingPrivateData) o;
            if (getWallet() != compareData.getWallet()) {
                return false;
            }

            if (getCheckType() != compareData.getCheckType()) {
                return false;
            }

            if (getLevel() != compareData.getLevel()) {
                return false;
            }

            if (!getOrder().equals(compareData.getOrder())) {
                return false;
            }

            if (!getReward().equals(compareData.getReward())) {
                return false;
            }

            return true;
        }

        public static class NewOrderInfo {
            //byte[5]分别代表全部订单,已预约订单,待执行订单,已完成订单,已失效订单
            public byte[] state = new byte[]{0, 0, 0, 0, 0};
            public List<String> ids = new ArrayList<>();
        }
    }

    public static class PollingRewardData extends BaseEntity {
        private long completeCourseParent = 0;
        private long completeCourseTeacher = 0;
        private long inviteParent = 0;
        private long inviteTeacher = 0;
        private long specialOrder = 0;

        public boolean isRewardNew(PollingRewardData compareData) {
            if (App.getUser().isParent()) {
                return isCompleteCourseParentNew(compareData) ||
                        isInviteParentNew(compareData);

            } else {
                return isCompleteCourseTeacherNew(compareData) ||
                        isInviteTeacherNew(compareData) ||
                        isSpreadNew(compareData);
            }
        }

        public boolean isCompleteCourseParentNew(PollingRewardData compareData) {
            return completeCourseParent != compareData.completeCourseParent;
        }

        public boolean isCompleteCourseTeacherNew(PollingRewardData compareData) {
            return completeCourseTeacher != compareData.completeCourseTeacher;
        }

        public boolean isInviteParentNew(PollingRewardData compareData) {
            return inviteParent != compareData.inviteParent;
        }

        public boolean isInviteTeacherNew(PollingRewardData compareData) {
            return inviteTeacher != compareData.inviteTeacher;
        }

        public boolean isSpreadNew(PollingRewardData compareData) {
            return specialOrder != compareData.specialOrder;
        }

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
            if (!(o instanceof PollingRewardData)) {
                return false;
            }

            PollingRewardData compareData = (PollingRewardData) o;

            if (getCompleteCourseParent() != compareData.getCompleteCourseParent()) {
                return false;
            }

            if (getCompleteCourseTeacher() != compareData.getCompleteCourseTeacher()) {
                return false;
            }

            if (getInviteParent() != compareData.getInviteParent()) {
                return false;
            }

            if (getInviteTeacher() != compareData.getInviteTeacher()) {
                return false;
            }

            if (getSpecialOrder() != compareData.getSpecialOrder()) {
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

            if (timestamp != compareDate.timestamp) {
                return false;
            }

            return true;
        }
    }

    @Override
    public boolean equals(Object other) {
        return equals(other, false);
    }

    public boolean equals(Object other, boolean replace) {
        if (!(other instanceof PollingData)) {
            return false;
        }

        PollingData compareData = (PollingData) other;

        //为了减少判断的操作次数
        if (dataString.equals(compareData.dataString)) {
            LogUtils.i(Constants.Tag.POLLING_TAG, "success");
            return true;
        }

        if (!common.equals(compareData.getCommon())) {
            return false;
        }

        if (!mine.equals(compareData.getMine())) {
            return false;
        }

        //相等但dataString不一样
        if (replace) {
            dataString = compareData.dataString;
        }

        return true;
    }
}
