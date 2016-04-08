package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.Message;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.RGetMessages;
import com.scut.easyfe.ui.adapter.MessageAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

public class MessageCenterActivity extends BaseActivity {
    private ListView mMessageListView;
    private BaseAdapter mMessageAdapter;
    private List<Message> mMessages;
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_message_center);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("消息中心");
        mMessageListView = OtherUtils.findViewById(this, R.id.message_center_lv_content);

        mMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, mMessages);
        mMessageListView.setAdapter(mMessageAdapter);
    }

    @Override
    protected void fetchData() {
        startLoading("获取系统消息中");
        RequestManager.get().execute(new RGetMessages(), new RequestListener<List<Message>>() {
            @Override
            public void onSuccess(RequestBase request, List<Message> result) {
                mMessages.addAll(result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMessageAdapter.notifyDataSetChanged();
                    }
                });
                stopLoading();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
                stopLoading();
            }
        });
    }

    public void onBackClick(View view){
        finish();
    }
}
