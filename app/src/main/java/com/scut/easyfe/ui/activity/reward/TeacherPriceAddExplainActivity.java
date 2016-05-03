package com.scut.easyfe.ui.activity.reward;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 完成课时单价增加标准 页面
 */
public class TeacherPriceAddExplainActivity extends BaseActivity {
    private TextView mExplainTextView;
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teacher_price_add_explain);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("完成课时单价增加标准");
        mExplainTextView = OtherUtils.findViewById(this, R.id.teacher_price_add_tv_explain);

        initTextView();
    }

    private void initTextView() {
        ArrayList<String> specialTextList = new ArrayList<>();

        String explainText = getResources().getString(R.string.teacher_price_add_explain_2);
        String reg = "\\d+元 | \\d+%";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(explainText);

        while(matcher.find()) {
            specialTextList.add(matcher.group());
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(explainText);

        int start = 0;
        int end = 0;
        for (String specialText :
                specialTextList) {
            start = explainText.indexOf(specialText);
            end = start + specialText.length();
            builder.setSpan(new ForegroundColorSpan(mResources.getColor(R.color.theme_color)),
                    start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        mExplainTextView.setText(builder);
    }

    public void onBackClick(View view){
        finish();
    }
}
