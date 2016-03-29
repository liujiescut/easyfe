package com.scut.easyfe.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.Message;
import com.scut.easyfe.ui.adapter.MessageAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;

public class MessageCenterActivity extends BaseActivity {
    private ListView mMessageListView;
    private ArrayList<Message> mMessages;
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_message_center);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText("消息中心");
        mMessageListView = OtherUtils.findViewById(this, R.id.message_center_lv_content);

        mMessages = Message.getTestMessage();
        mMessageListView.setAdapter(new MessageAdapter(this, mMessages));
    }

    @Override
    protected void fetchData() {
        super.fetchData();
    }

    public void onBackClick(View view){
        finish();
    }
}
