package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 用户与积分规则页面
 */
public class ScoreProtocolActivity extends BaseActivity {

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_score_protocol);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("用户与积分规则");

        if(App.getUser().isTeacher()){
            ((TextView) OtherUtils.findViewById(this, R.id.score_protocol_tv_user_type)).setText("家教等级");
            ((TextView) OtherUtils.findViewById(this, R.id.score_protocol_tv_level_1)).setText("普通教员");
            ((TextView) OtherUtils.findViewById(this, R.id.score_protocol_tv_level_2)).setText("银卡教员");
            ((TextView) OtherUtils.findViewById(this, R.id.score_protocol_tv_level_3)).setText("金卡教员");
            ((TextView) OtherUtils.findViewById(this, R.id.score_protocol_tv_level_4)).setText("黑金卡教员");
            ((TextView) OtherUtils.findViewById(this, R.id.score_protocol_tv_level_5)).setText("钻石卡教员");
        }
    }

    public void onBackClick(View view){
        finish();
    }
}
