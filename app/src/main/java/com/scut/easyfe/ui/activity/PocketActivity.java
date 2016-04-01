package com.scut.easyfe.ui.activity;

import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.OtherUtils;

public class PocketActivity extends BaseActivity {

    private TextView mTitleRightTextView;
    private TextView mBalanceTextView;
    private TextView mHasWithdrawTextView;
    private TextView mWithdrawingTextView;
    private TextView mAlipayTextView;
    private TextView mWechatTextView;
    private TextView mBankNameTextView;
    private TextView mCardNumTextView;

    private int mState = Constants.Identifier.STATE_NORMAL;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_pocket);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("我的钱包");
        mTitleRightTextView = (TextView) findViewById(R.id.titlebar_tv_right);
        mTitleRightTextView.setVisibility(View.VISIBLE);
        mTitleRightTextView.setText(R.string.edit);

        mBalanceTextView = OtherUtils.findViewById(this, R.id.pocket_tv_account_balance);
        mHasWithdrawTextView = OtherUtils.findViewById(this, R.id.pocket_tv_has_withdraw_count);
        mWithdrawingTextView = OtherUtils.findViewById(this, R.id.pocket_tv_withdrawing_count);
        mAlipayTextView = OtherUtils.findViewById(this, R.id.pocket_tv_alipay);
        mWechatTextView = OtherUtils.findViewById(this, R.id.pocket_tv_wechat);
        mBankNameTextView = OtherUtils.findViewById(this, R.id.pocket_tv_bank_name);
        mCardNumTextView = OtherUtils.findViewById(this, R.id.pocket_tv_card_num);
    }

    @Override
    protected void initListener() {
    }

    /**
     * 点击返回
     */
    public void onBackClick(View view) {
        finish();
    }

    /**
     * 点击右上角按钮(编辑/保存)
     */
    public void onRightClick(View view) {
        mState = (mState == Constants.Identifier.STATE_EDIT) ? Constants.Identifier.STATE_NORMAL : Constants.Identifier.STATE_EDIT;
        boolean isEdit = (mState == Constants.Identifier.STATE_EDIT);
        mTitleRightTextView.setText(isEdit ? R.string.save : R.string.edit);
        int textColor = mResources.getColor(isEdit ? R.color.text_area_editable_text_color : R.color.text_area_text_color);
        mAlipayTextView.setTextColor(textColor);
        mWechatTextView.setTextColor(textColor);
        mBankNameTextView.setTextColor(textColor);
        mCardNumTextView.setTextColor(textColor);
    }

    /**
     * 点击提现
     */
    public void onWithdrawClick(View view) {
        if (mState == Constants.Identifier.STATE_NORMAL) {
            new AlertView("提现到", null, "取消", null,
                    new String[]{"支付宝", "微信", "银行卡"},
                    this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    if(position == 0){
                        toast("支付宝");
                    }else if(position == 1){
                        toast("支付宝");
                    }else if(position == 2){
                        toast("银行卡");
                    }
                }
            }).setButtonTextColor(R.color.theme_color
            ).show();
        }else{
            toast("请先保存");
        }
    }

    /**
     * 点击支付宝账户
     */
    public void onAlipayAccountClick(View view) {
        if (mState == Constants.Identifier.STATE_EDIT) {
            DialogUtils.makeInputDialog(this, "支付宝账号", InputType.TYPE_CLASS_TEXT, new DialogUtils.OnInputListener() {
                @Override
                public void onFinish(String msg) {
                    mAlipayTextView.setText(msg);
                }
            }).show();
        }
    }

    /**
     * 点击微信账户
     */
    public void onWechatAccountClick(View view) {
        if (mState == Constants.Identifier.STATE_EDIT) {
            DialogUtils.makeInputDialog(this, "微信号", InputType.TYPE_CLASS_TEXT, new DialogUtils.OnInputListener() {
                @Override
                public void onFinish(String msg) {
                    mWechatTextView.setText(msg);
                }
            }).show();
        }
    }


    /**
     * 点击开户银行
     */
    public void onBankNameClick(View view) {
        if (mState == Constants.Identifier.STATE_EDIT) {
            toast("点击提现");
        }
    }

    /**
     * 点击银行卡号
     */
    public void onCardNumClick(View view) {
        if (mState == Constants.Identifier.STATE_EDIT) {
            DialogUtils.makeInputDialog(this, "银行卡号", InputType.TYPE_CLASS_NUMBER, new DialogUtils.OnInputListener() {
                @Override
                public void onFinish(String msg) {
                    mCardNumTextView.setText(msg);
                }
            }).show();
        }
    }
}
