package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Handler;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.ACache;


public class SplashActivity extends BaseActivity {

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initView() {
        super.initView();
        YoYo.with(Techniques.Pulse).duration(10000).playOn(findViewById(R.id.splash_iv_container));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkAndStartActivity();
            }
        }, 1300);
    }

    private void checkAndStartActivity() {

        boolean isFirstStart = App.getSpUtils().isFirstStart(mContext);
//        boolean isFirstStart = true;

        if (isFirstStart) {
            // 跳转应用介绍处
            redirectToActivity(this, IntroActivity.class);
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            // 清除在目标之上的 Activity, 且清除已存在的目标 Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        finish();
    }
}
