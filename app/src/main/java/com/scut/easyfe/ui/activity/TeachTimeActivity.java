package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.book.MultiBookTime;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.user.teacher.RTeacherMultiBookTimeModify;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 教授时间页面
 *
 * @author jay
 */
public class TeachTimeActivity extends BaseActivity {
    private LinearLayout mContainerLinearLayout;
    private ArrayList<String> mWeek = new ArrayList<>();
    private ArrayList<ArrayList<String>> mTime = new ArrayList<>();

    private OptionsPickerView<String> mPicker;

    /**
     * 第一个纬度表示星期几,第二个纬度表示上午下午晚上是否有空(跟后台约定)
     * 比如mCourseTime[1][0]为1表示周一有空,CourseTime[1][1]为1表示周一上午有空(为0表示没空)
     */
    private int[][] mCourseTime = new int[7][4];

    private User mUser;
    private int mFromType = Constants.Identifier.TYPE_REGISTER;

    @Override
    protected void onResume() {
        super.onResume();
        mUser = App.getUser(false).getCopy();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mFromType = extras.getInt(Constants.Key.TO_TEACH_TIME_ACTIVITY_TYPE, Constants.Identifier.TYPE_REGISTER);
            }
        }

        mWeek.add(getResources().getString(R.string.sunday));
        mWeek.add(getResources().getString(R.string.monday));
        mWeek.add(getResources().getString(R.string.tuesday));
        mWeek.add(getResources().getString(R.string.wednesday));
        mWeek.add(getResources().getString(R.string.thursday));
        mWeek.add(getResources().getString(R.string.friday));
        mWeek.add(getResources().getString(R.string.saturday));

        for (int i = 0; i < mWeek.size(); i++) {
            ArrayList<String> times = new ArrayList<>();
            times.add(getResources().getString(R.string.morning));
            times.add(getResources().getString(R.string.afternoon));
            times.add(getResources().getString(R.string.night));
            mTime.add(times);
        }

        mUser = App.getUser(false).getCopy();

        for (MultiBookTime time :
                mUser.getTeacherMessage().getMultiBookTime()) {
            if(time.isOk()) {
                mCourseTime[time.getWeekDay()][0] = 1;
                mCourseTime[time.getWeekDay()][1] = time.isMorning() ? 1 : 0;
                mCourseTime[time.getWeekDay()][2] = time.isAfternoon() ? 1 : 0;
                mCourseTime[time.getWeekDay()][3] = time.isEvening() ? 1 : 0;
            }
        }
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teach_time);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("授课时间");
        if(mFromType == Constants.Identifier.TYPE_MODIFY){
            ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_right)).setText("保存");
            ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_right)).setVisibility(View.VISIBLE);
        }

        mContainerLinearLayout = OtherUtils.findViewById(this, R.id.teach_time_ll_container);

        mPicker = new OptionsPickerView<>(this);
        mPicker.setPicker(mWeek, mTime, false);
        mPicker.setCyclic(false);

        for(int i = 0; i < 7; i++){
            if(mCourseTime[i][0] == 1){
                for(int j  = 1; j <= 3; j++){
                    if(mCourseTime[i][j] == 1) {
                        addCourseTimeItem(i, j - 1);
                    }
                }
            }
        }
    }

    @Override
    protected void initListener() {
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mCourseTime[options1][0] = 1;
                mCourseTime[options1][option2 + 1] = 1;
                addCourseTimeItem(options1, option2);
            }
        });
    }

    /**
     * 点击加号添加授课时间
     */
    public void onAddClick(View view) {
        mPicker.show();
    }

    /**
     * 添加一个授课时间
     *
     * @param option1 授课星期对应索引
     * @param option2 具体时间(上午\中午\晚上)对应索引
     */
    private void addCourseTimeItem(final int option1, int option2) {
        String week = mWeek.get(option1);
        String time = mTime.get(option1).get(option2);

        View itemView = mContainerLinearLayout.findViewById(option1);
        if (null == itemView) {
            itemView = getLayoutInflater().inflate(R.layout.item_course_time, null);
            itemView.setId(option1);
            final View finalItemView = itemView;
            itemView.findViewById(R.id.item_course_time).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.makeChooseDialog(mContext, "确认删除?", "删除之后这天数据将被删除,需要重新设置", new DialogUtils.OnChooseListener() {
                        @Override
                        public void onChoose(boolean sure) {
                            if (sure) {
                                mContainerLinearLayout.removeView(finalItemView);
                                for(int i = 0; i < 4; i++){
                                    mCourseTime[option1][i] = 0;
                                }
                            }
                        }
                    });
                }
            });
            mContainerLinearLayout.addView(itemView);
        } else {
            time = "";
            if (mCourseTime[option1][1] == 1) {
                time += "上午  ";
            }
            if (mCourseTime[option1][2] == 1) {
                time += "下午  ";
            }
            if (mCourseTime[option1][3] == 1) {
                time += "晚上";
            }
        }

        ((TextView) itemView.findViewById(R.id.item_course_week)).setText(week);
        ((TextView) itemView.findViewById(R.id.item_course_time)).setText(time);
    }

    public void onRightClick(View view){
        saveAndValidate();
    }

    /**
     * 点击近两月时间特别安排
     */
    public void onDetailPlanClick(View view) {
        if (!saveAndValidate()) {
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.TO_SPECIAL_TIME_ACTIVITY_TYPE, mFromType);
        redirectToActivity(mContext, SpecialTimeActivity.class, bundle);
    }

    public void onBackClick(View view) {
        saveAndValidate();
        finish();
    }

    private boolean saveAndValidate() {
        List<MultiBookTime> times = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (mCourseTime[i][0] == 1) {
                MultiBookTime time = new MultiBookTime();
                time.setWeekDay(i);
                time.setIsOk(true);
                time.setMorning(mCourseTime[i][1] == 1);
                time.setAfternoon(mCourseTime[i][2] == 1);
                time.setEvening(mCourseTime[i][3] == 1);
                times.add(time);
            }
        }

        if (times.size() == 0) {
            toast("请先选择授课时间");
            return false;
        }

        mUser.getTeacherMessage().getMultiBookTime().clear();
        mUser.getTeacherMessage().setMultiBookTime(times);
        if(mFromType == Constants.Identifier.TYPE_REGISTER) {
            App.setUser(mUser);

        }else{
            RequestManager.get().execute(new RTeacherMultiBookTimeModify(mUser), new RequestListener<JSONObject>() {
                @Override
                public void onSuccess(RequestBase request, JSONObject result) {
                    App.setUser(mUser);
                    toast("修改成功");
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                }
            });
        }

        return true;
    }
}
