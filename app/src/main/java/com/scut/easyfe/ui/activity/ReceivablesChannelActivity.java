package com.scut.easyfe.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;

/**
 * 收款渠道页面
 * @author jay
 */
public class ReceivablesChannelActivity extends BaseActivity {

    private EditText mAlipayEditText;
    private EditText mWechatEditText;
    private EditText mBankCardEditText;
    private TextView mBankNameTextView;

    private OptionsPickerView<String> mPicker;

    private static ArrayList<String> mBankNames = new ArrayList<>();

    static {
        mBankNames.add("工商银行");
        mBankNames.add("中国银行");
        mBankNames.add("建设银行");
        mBankNames.add("邮政储蓄");
    }

    private User mUser;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_receivables_channel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser = App.getUser(false);
    }

    @Override
    protected void initData() {
        mUser = App.getUser(false);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("家教注册 - 收款渠道");

        mPicker = new OptionsPickerView<>(mContext);
        mPicker.setPicker(mBankNames);
        mPicker.setCyclic(false);

        mAlipayEditText = OtherUtils.findViewById(this, R.id.receivables_channel_et_alipay);
        mWechatEditText = OtherUtils.findViewById(this, R.id.receivables_channel_et_wechat);
        mBankCardEditText = OtherUtils.findViewById(this, R.id.receivables_channel_et_bank_number);
        mBankNameTextView = OtherUtils.findViewById(this, R.id.receivables_channel_tv_bank_name);

        mAlipayEditText.setText(mUser.getBusiness().getAli());
        mWechatEditText.setText(mUser.getBusiness().getWechat());
        if(mUser.getBusiness().getBank().getName().length() != 0) {
            mBankNameTextView.setText(mUser.getBusiness().getBank().getName());
            mBankCardEditText.setText(mUser.getBusiness().getBank().getAccount());
        }
    }

    @Override
    protected void initListener() {
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mBankNameTextView.setText(mBankNames.get(options1));
            }
        });
    }

    /**
     * 点击返回
     */
    public void onBackClick(View view){
        finish();
    }

    /**
     * 点击选择银行名称
     */
    public void onBankNameClick(View view){
        OtherUtils.hideSoftInputWindow(view.getWindowToken());
        mPicker.show();
    }

    /**
     * 点击保存
     */
    public void onSaveClick(View view){
        mUser.getBusiness().setAli(mAlipayEditText.getText().toString());
        mUser.getBusiness().setWechat(mWechatEditText.getText().toString());
        mUser.getBusiness().getBank().setName(mBankNameTextView.getText().toString());
        mUser.getBusiness().getBank().setAccount(mBankCardEditText.getText().toString());

        if(!validate(mUser)){
            return;
        }

        App.setUser(mUser);

        redirectToActivity(mContext, SelfIntroduceActivity.class);
    }

    private boolean validate(User user){
        if(user.getBusiness().getAli().length() == 0){
            toast("请输入支付宝帐号");
            return false;
        }

        if(user.getBusiness().getWechat().length() == 0){
            toast("请输入微信帐号");
            return false;
        }

        return true;
    }
}
