package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

public class PocketActivity extends BaseActivity {
    private TextView mBalanceTextView;
    private TextView mHasWithdrawTextView;
    private TextView mWithdrawingTextView;
    private TextView mAlipayTextView;
    private TextView mWechatTextView;
    private TextView mBankNameTextView;
    private TextView mCardNumTextView;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_pocket);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText("我的钱包");
        (findViewById(R.id.titlebar_tv_right)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.titlebar_tv_right)).setText("编辑");

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
        super.initListener();
    }

    /**
     * 点击提现
     */
    public void onWithdrawClick(View view){

    }
}
