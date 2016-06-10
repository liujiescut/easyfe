package com.scut.easyfe.ui.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.entity.UserLevel;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.user.RGetUserLevel;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

import java.util.Locale;

/**
 * 我的等级页面
 * @author jay
 */
public class LevelActivity extends BaseActivity {
    private TextView mUserTypeTextView;
    private TextView mLevelLabelTextView;
    private TextView mLevelTextView;
    private TextView mCompleteTimeTextView;
    private TextView mScoreTextView;
    private TextView mScoreLabelTextView;

    private User mUser;

    private boolean mIsLoadingClosedByUser = true;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_level);
    }

    @Override
    protected void initData() {
        mUser = App.getUser();
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("我的等级");

        mUserTypeTextView = OtherUtils.findViewById(this, R.id.level_tv_user_type);
        mLevelLabelTextView = OtherUtils.findViewById(this, R.id.level_tv_level_label);
        mLevelTextView = OtherUtils.findViewById(this, R.id.level_tv_level);
        mCompleteTimeTextView = OtherUtils.findViewById(this, R.id.level_tv_complete_time);
        mScoreTextView = OtherUtils.findViewById(this, R.id.level_tv_score);
        mScoreLabelTextView = OtherUtils.findViewById(this, R.id.level_tv_score_label);

        mUserTypeTextView.setText(mUser.isParent() ? "家长等级" : "家教等级");
        mLevelLabelTextView.setText(mUser.isParent() ? "家长等级" : "家教等级");
        mScoreLabelTextView.setText(mUser.isParent() ? "家长积分" : "家教积分");
    }

    @Override
    protected void fetchData() {
        startLoading("加载数据中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(mIsLoadingClosedByUser){
                    finish();
                }
            }
        });

        RequestManager.get().execute(new RGetUserLevel(), new RequestListener<UserLevel>() {
            @Override
            public void onSuccess(RequestBase request, UserLevel result) {
                mLevelTextView.setText(result.getLevel());
                mCompleteTimeTextView.setText(String.format(Locale.CHINA, "%d 小时", result.getTime() / 60));
                mScoreTextView.setText(String.format(Locale.CHINA, "%d 分", result.getScore()));
                mIsLoadingClosedByUser = false;
                stopLoading();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
                stopLoading();
            }
        });
    }

    public void onDetailClick(View view){
        redirectToActivity(this, ScoreProtocolActivity.class);
    }

    public void onBackClick(View view){
        finish();
    }
}
