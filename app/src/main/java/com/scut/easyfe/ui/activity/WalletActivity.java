package com.scut.easyfe.ui.activity;

import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Bank;
import com.scut.easyfe.entity.Wallet;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.wallet.RGetWalletInfo;
import com.scut.easyfe.network.request.wallet.RModifyWalletInfo;
import com.scut.easyfe.network.request.wallet.RWithdraw;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class WalletActivity extends BaseActivity {

    private TextView mTitleRightTextView;
    private TextView mBalanceTextView;
    private TextView mHasWithdrawTextView;
    private TextView mWithdrawingTextView;
    private TextView mAlipayTextView;
    private TextView mWechatTextView;
    private TextView mBankNameTextView;
    private TextView mCardNumTextView;

    private OptionsPickerView<String> mPicker;

    private int mState = Constants.Identifier.STATE_NORMAL;

    private boolean mIsLoadingDismissByUser = true;
    private Wallet mWallet = new Wallet();

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_wallet);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("我的钱包");
        mTitleRightTextView = OtherUtils.findViewById(this, R.id.titlebar_tv_right);
        mTitleRightTextView.setVisibility(View.VISIBLE);
        mTitleRightTextView.setText(R.string.edit);

        mBalanceTextView = OtherUtils.findViewById(this, R.id.wallet_tv_account_balance);
        mHasWithdrawTextView = OtherUtils.findViewById(this, R.id.wallet_tv_has_withdraw_count);
        mWithdrawingTextView = OtherUtils.findViewById(this, R.id.wallet_tv_withdrawing_count);
        mAlipayTextView = OtherUtils.findViewById(this, R.id.wallet_tv_alipay);
        mWechatTextView = OtherUtils.findViewById(this, R.id.wallet_tv_wechat);
        mBankNameTextView = OtherUtils.findViewById(this, R.id.wallet_tv_bank_name);
        mCardNumTextView = OtherUtils.findViewById(this, R.id.wallet_tv_card_num);

        mPicker = new OptionsPickerView<>(mContext);
        mPicker.setPicker(Constants.Data.bankNameList);
        mPicker.setCyclic(false);

        if(!App.getUser().isParent()){
            ((View)OtherUtils.findViewById(this, R.id.wallet_ll_my_tickets)).setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mBankNameTextView.setText(Constants.Data.bankNameList.get(options1));
            }
        });
    }

    @Override
    protected void fetchData() {
        startLoading("加载数据中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mIsLoadingDismissByUser) {
                    toast("加载数据失败");
                    finish();
                }
            }
        });

        RequestManager.get().execute(new RGetWalletInfo(App.getUser().getToken()), new RequestListener<Wallet>() {
            @Override
            public void onSuccess(RequestBase request, Wallet wallet) {
                mWallet = wallet;
                mBalanceTextView.setText(String.format(Locale.CHINA, "%.2f 元", mWallet.getBalance() / 100));
                mWithdrawingTextView.setText(String.format(Locale.CHINA, "%.2f 元", mWallet.getWithdrawing() / 100));
                mHasWithdrawTextView.setText(String.format(Locale.CHINA, "%.2f 元", mWallet.getHaveWithdraw() / 100));
                mAlipayTextView.setText(mWallet.getAli());
                mWechatTextView.setText(mWallet.getWechat());
                mBankNameTextView.setText(mWallet.getBank().getName());
                mCardNumTextView.setText(mWallet.getBank().getAccount());

                mIsLoadingDismissByUser = false;
                stopLoading();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
                mIsLoadingDismissByUser = false;
                stopLoading();
            }
        });
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
        if (mState == Constants.Identifier.STATE_EDIT) {
            if (!validate()) {
                return;
            }

            mWallet.setAli(mAlipayTextView.getText().toString());
            mWallet.setWechat(mWechatTextView.getText().toString());
            mWallet.getBank().setName(mBankNameTextView.getText().toString());
            mWallet.getBank().setAccount(mCardNumTextView.getText().toString());

            RequestManager.get().execute(new RModifyWalletInfo(App.getUser().getToken(), mWallet), new RequestListener<JSONObject>() {
                @Override
                public void onSuccess(RequestBase request, JSONObject result) {
                    toast("修改成功");
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                }
            });
        }

        mState = (mState == Constants.Identifier.STATE_EDIT) ? Constants.Identifier.STATE_NORMAL : Constants.Identifier.STATE_EDIT;
        boolean isEdit = (mState == Constants.Identifier.STATE_EDIT);
        mTitleRightTextView.setText(isEdit ? R.string.save : R.string.edit);
        int textColor = mResources.getColor(isEdit ? R.color.text_area_editable_text_color : R.color.text_area_text_color);
        mAlipayTextView.setTextColor(textColor);
        mWechatTextView.setTextColor(textColor);
        mBankNameTextView.setTextColor(textColor);
        mCardNumTextView.setTextColor(textColor);
    }

    private boolean validate() {
        if (mAlipayTextView.getText().toString().length() == 0) {
            toast("请输入支付宝帐号");
            return false;
        }

        if (mWechatTextView.getText().toString().length() == 0) {
            toast("请输入微信帐号");
            return false;
        }

        return true;
    }

    /**
     * 点击提现
     */
    public void onWithdrawClick(View view) {
        if (mState == Constants.Identifier.STATE_NORMAL) {
            final JSONObject json = new JSONObject();

            AlertView mPayWayAlertView = new AlertView("提现到", null, "取消", null,
                    new String[]{"支付宝", "微信", "银行卡"},
                    this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    try {
                        if (position == 0) {
                            json.put("ali", mAlipayTextView.getText().toString());
                        } else if (position == 1) {
                            json.put("wechat", mWechatTextView.getText().toString());
                        } else if (position == 2) {
                            Bank bank = new Bank();
                            bank.setName(mBankNameTextView.getText().toString());
                            bank.setAccount(mCardNumTextView.getText().toString());
                            json.put("bank", bank.getBankJson());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }).setButtonTextColor(R.color.theme_color);

            mPayWayAlertView.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(Object o) {
                    if(json.toString().length() == 0){
                        return;
                    }

                    DialogUtils.makeInputDialog(mContext, "提现金额", InputType.TYPE_CLASS_NUMBER, new DialogUtils.OnInputListener() {
                        @Override
                        public void onFinish(String msg) {
                            if (msg.length() == 0 || json.toString().length() == 0) {
                                return;
                            }

                            doWithdraw(json, Integer.parseInt(msg));
                        }
                    }).show();
                }
            });

            mPayWayAlertView.show();
        } else {
            toast("请先保存");
        }
    }

    private void doWithdraw(JSONObject way, int money){
        if (money <= 0){
            toast("请输入有效金额");
            return;
        }

        RequestManager.get().execute(new RWithdraw(App.getUser().getToken(), money, way), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(RequestBase request, JSONObject result) {
                toast(result.optString("message"));
                fetchData();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
            }
        });
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
            mPicker.show();
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

    /**
     * 点击我的优惠券
     */
    public void onMyTicketClick(View view){
        redirectToActivity(this, CouponActivity.class);
    }
}
