package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

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

    public void onDetailClick(View view){
        toast("detail");
    }

    public void onBackClick(View view){
        finish();
    }
}
