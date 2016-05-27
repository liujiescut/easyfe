package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RGetCashTicket;
import com.scut.easyfe.network.request.user.parent.RCommentTeacher;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

import io.techery.properratingbar.ProperRatingBar;

public class EvaluationActivity extends BaseActivity {
    private ProperRatingBar mChildLikeRatingBar;
    private ProperRatingBar mAbilityRatingBar;
    private ProperRatingBar mOnTimeRatingBar;
    private EditText mContentEditText;
    private Order mOrder = new Order();
    private boolean mHasGetCashTicket = false;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_evaluation);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mOrder = (Order)extras.getSerializable(Constants.Key.ORDER);
            }
        }
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("已完成订单");
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_right)).setText("提交");
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_right)).setVisibility(View.VISIBLE);

        mChildLikeRatingBar = OtherUtils.findViewById(this, R.id.evaluation_prb_child_like);
        mAbilityRatingBar = OtherUtils.findViewById(this, R.id.evaluation_prb_profession);
        mOnTimeRatingBar = OtherUtils.findViewById(this, R.id.evaluation_prb_child_on_time);
        mContentEditText = OtherUtils.findViewById(this, R.id.evaluation_et_content);
    }

    public void onBackClick(View view) {
        finish();
    }

    public void onRightClick(View view) {
        if (mContentEditText.getText().toString().length() == 0) {
            toast("请先输入相关评论喔");
            return;
        }

        if(!mHasGetCashTicket){
            //Todo
        }

        startLoading("领取中");
        RequestManager.get().execute(new RCommentTeacher(App.getUser().getToken(), mOrder.get_id(),
                mContentEditText.getText().toString(), mAbilityRatingBar.getRating(),
                mOnTimeRatingBar.getRating(), mChildLikeRatingBar.getRating()), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(RequestBase request, JSONObject result) {
                toast(result.optString("message"));
                stopLoading();
                redirectToActivity(mContext, MainActivity.class);
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
                stopLoading();
            }
        });
    }

    public void onComplaintClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.CALLBACK_TYPE, Constants.Identifier.CALLBACK_COMPLAINTS);
        redirectToActivity(this, CallbackActivity.class, bundle);
    }

    public void onMoneyTicketClick(View view) {
        startLoading("领取中");
        RequestManager.get().execute(new RGetCashTicket(mOrder.get_id()), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(RequestBase request, JSONObject result) {
                toast(result.optString("message"));
                stopLoading();
                mHasGetCashTicket = true;
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                stopLoading();
            }
        });
    }
}
