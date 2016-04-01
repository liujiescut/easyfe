package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Order;
import com.scut.easyfe.entity.ToSelectItem;
import com.scut.easyfe.ui.adapter.SelectItemAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.customView.CircleImageView;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.ImageUtils;
import com.scut.easyfe.utils.ListViewUtil;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;

/**
 * 确认家教信息页面
 */
public class TeacherInfoActivity extends BaseActivity {
    private TextView mNameTextView;
    private TextView mPriceTextView;
    private TextView mBaseInfoTextView;
    private TextView mScoreInfoTextView;
    private TextView mSelfIntroduceTextView;
    private TextView mSeeCommentsTextView;
    private TextView mMultiReserveHintTextView;
    private TextView mMultiReserveTimesTextView;
    private CircleImageView mAvatarImageView;
    private LinearLayout mMultiReserveTimesLinearLayout;
    private ListView mCommentListView;
    private TextView mDoReserveTextView;

    private Order mOrder;
    private int mReserveType = Constants.Identifier.RESERVE_MULTI;
    private int mReserveTimes = 1;
    private int mFromType = Constants.Identifier.TYPE_RESERVE;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teacher_info);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(null != intent){
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mFromType = extras.getInt(Constants.Key.TO_TEACHER_INFO_ACTIVITY_TYPE, Constants.Identifier.TYPE_RESERVE);
                if(mFromType == Constants.Identifier.TYPE_RESERVE) {
                    mReserveType = extras.getInt(Constants.Key.RESERVE_WAY, Constants.Identifier.RESERVE_MULTI);
                }
                mOrder = (Order) extras.getSerializable(Constants.Key.ORDER);
            }else{
                mOrder = new Order();
            }
        }else{
            mOrder = new Order();
        }
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText(String.format("%s 老师", mOrder.getTeacherName() ));

        mNameTextView = OtherUtils.findViewById(this, R.id.item_search_result_tv_teacher);
        mPriceTextView = OtherUtils.findViewById(this, R.id.item_search_result_tv_price);
        mBaseInfoTextView = OtherUtils.findViewById(this, R.id.item_search_result_tv_content);
        mAvatarImageView = OtherUtils.findViewById(this, R.id.item_search_result_civ_avatar);
        mScoreInfoTextView = OtherUtils.findViewById(this, R.id.teacher_info_tv_score_content);
        mSelfIntroduceTextView = OtherUtils.findViewById(this, R.id.teacher_info_tv_self_introduce_content);
        mSeeCommentsTextView = OtherUtils.findViewById(this, R.id.teacher_info_tv_comment_more);
        mMultiReserveHintTextView = OtherUtils.findViewById(this, R.id.teacher_info_tv_multi_reserve_info);
        mMultiReserveTimesTextView = OtherUtils.findViewById(this, R.id.teacher_info_tv_reserve_times);
        mMultiReserveTimesLinearLayout = OtherUtils.findViewById(this, R.id.teacher_info_ll_multi_reserve_times);
        mCommentListView = OtherUtils.findViewById(this, R.id.teacher_info_lv_comments);
        mDoReserveTextView = OtherUtils.findViewById(this, R.id.teacher_info_tv_do_reserve);

        if(mFromType == Constants.Identifier.TYPE_SEE_TEACHER_INFO){
            mDoReserveTextView.setVisibility(View.GONE);
            mMultiReserveHintTextView.setVisibility(View.GONE);
            mMultiReserveTimesLinearLayout.setVisibility(View.GONE);
            mMultiReserveTimesTextView.setVisibility(View.GONE);
        }else {
            if (mReserveType == Constants.Identifier.RESERVE_SINGLE) {
                mMultiReserveHintTextView.setVisibility(View.GONE);
                mMultiReserveTimesLinearLayout.setVisibility(View.GONE);
                mMultiReserveTimesTextView.setVisibility(View.GONE);
            } else {
                mMultiReserveTimesTextView.setText(mReserveTimes + "");
            }

            if(mOrder.getTip() != 0){
                DialogUtils.makeConfirmDialog(mContext, null, getString(R.string.add_tip_info));
            }
        }

        showTeacherInfo();

    }

    private void showTeacherInfo(){
        String priceString = String.format("%.2f 元/次", mOrder.getPrice());
        if(mOrder.getTip() != 0){
            mPriceTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.icon_detail_question_small, 0);
            priceString = String.format("%.2f 元/次", mOrder.getPrice() + mOrder.getTip());
            priceString += String.format("(包含交通补贴%.0f元)", mOrder.getTip());
        }

        mPriceTextView.setText(priceString);
        mNameTextView.setText(mOrder.getTeacherName());
        mBaseInfoTextView.setText(Order.getBaseInfo(mOrder));

        ImageUtils.displayImage(mOrder.getTeacherAvatar(), mAvatarImageView);

        ArrayList<String> comments = new ArrayList<>();
        ArrayList<ToSelectItem> commentItems = new ArrayList<>();
        comments.add("掌柜人不错，鞋很好，很热情。");
        comments.add("很难得的正品，网购以来最满意的了。");
        comments.add("本来有点担心，不过去验证了，是行货，是个好卖家。");
        for (String comment :
                comments) {
            commentItems.add(new ToSelectItem(comment, false));
        }
        SelectItemAdapter adapter = new SelectItemAdapter(mContext, commentItems);
        adapter.setSelectable(false);
        mCommentListView.setAdapter(adapter);
        ListViewUtil.setListViewHeightBasedOnChildren(mCommentListView);
    }

    public void onReserveTimesClick(View view){
        DialogUtils.makeInputDialog(mContext, "预约次数", InputType.TYPE_CLASS_NUMBER, new DialogUtils.OnInputListener() {
            @Override
            public void onFinish(String msg) {
                try {
                    mReserveTimes = Integer.parseInt(msg);
                    mMultiReserveTimesTextView.setText(String.format("%s 次", msg));
                }catch (NumberFormatException e){
                    toast("请输入数字");
                }
            }
        }).show();
    }

    public void onMoreCommentsClick(View view){
        redirectToActivity(mContext, CommentsActivity.class);
    }

    public void onDoReservationClick(View view){
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.Key.ORDER, mOrder);
        if(mReserveType == Constants.Identifier.RESERVE_MULTI){
            bundle.putInt(Constants.Key.CONFIRM_ORDER_TYPE, Constants.Identifier.CONFIRM_ORDER_MULTI_RESERVE);
            bundle.putInt(Constants.Key.TEACH_WEEK, mReserveTimes);
        }else{
            bundle.putInt(Constants.Key.CONFIRM_ORDER_TYPE, Constants.Identifier.CONFIRM_ORDER_SINGLE_RESERVE);
        }
        redirectToActivity(mContext, ConfirmOrderActivity.class, bundle);
    }

    public void onBackClick(View view){
        finish();
    }
}
