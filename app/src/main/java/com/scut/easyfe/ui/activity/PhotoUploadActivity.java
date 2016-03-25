package com.scut.easyfe.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.BitmapUtils;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

import java.io.File;

public class PhotoUploadActivity extends BaseActivity {

    public static final int PICK_FROM_CAMERA = 0;                                               //intent跳转传递的参数跟返回值
    public static final int PICK_FROM_FILE = 1;
    public static final int ACTION_CROP = 2;

    private static final int IMAGE_WIDTH_RATE = 4;
    private static final int IMAGE_HEIGHT_RATE = 3;
    private static final int UPLOAD_AVATAR_SIZE = 256;


    private static final int TYPE_ID_CARD = 0;
    private static final int TYPE_STUDENT_CARD = 1;
    private static final int TYPE_AVATAR = 2;
    private int mPhotoType = -1;    //选择图片对应的类型

    private Bitmap newAvatar;
    private Uri imageCaptureUri;
    private boolean tryingAnotherCropMethod = false;

    private ImageView mIdCardImageView;
    private ImageView mStudentCardImageView;
    private ImageView mAvatarImageView;
    private AlertView mSelectAlertView;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_photo_upload);
    }

    @Override
    protected void initView() {
        //Todo 设置ImageView高度,参考lvyou
        mIdCardImageView = OtherUtils.findViewById(this, R.id.photo_upload_iv_id_card);
        mStudentCardImageView = OtherUtils.findViewById(this, R.id.photo_upload_iv_student_card);
        mAvatarImageView = OtherUtils.findViewById(this, R.id.photo_upload_iv_avatar);
        mSelectAlertView = new AlertView("上传头像", null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if(position == 0){
                    takePhoto();
                }else if(position == 1){
                    pickPhoto();
                }
            }
        });
    }

    @Override
    protected void initListener() {
    }

    /**
     * 上传身份证
     */
    public void uploadIdCard(View view){
        mPhotoType = TYPE_ID_CARD;
        mSelectAlertView.show();
    }

    /**
     * 上传学生证
     */
    public void uploadStudentCard(View view){
        mPhotoType = TYPE_STUDENT_CARD;
        mSelectAlertView.show();
    }

    /**
     * 上传头像
     */
    public void uploadAvatar(View view){
        mPhotoType = TYPE_AVATAR;
        mSelectAlertView.show();
    }

    /**
     * 调用系统直接拍照
     */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),
                "time_"
                        + String.valueOf(System.currentTimeMillis())
                        + ".jpg");
        imageCaptureUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                imageCaptureUri);
        if (checkIntentAndSd(intent))
            startActivityForResult(intent, PICK_FROM_CAMERA);

    }


    /**
     * 选择图片
     */
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        if (checkIntentAndSd(intent))
            startActivityForResult(intent, PICK_FROM_FILE);

    }

    /**
     * 检查SD卡是否存在
     * 检查隐式intent是否存在
     */
    private boolean checkIntentAndSd(Intent intent) {
        //检查
        if (!android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            toast("请先插入SD卡");
            return false;
        }
        ComponentName componentName = intent.resolveActivity(getPackageManager());
        if (componentName == null) {
            toast("找不到对应的应用");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_FILE:
                startPhotoZoom(data.getData());
                break;
            case PICK_FROM_CAMERA:
                startPhotoZoom(imageCaptureUri);
                break;
            case ACTION_CROP:
                if (!tryingAnotherCropMethod) {
                    if (data != null) {
                        // get the returned data
                        Bundle extras = data.getExtras();
                        // get the cropped bitmap
                        newAvatar = extras.getParcelable("data");
                        if (null != newAvatar) {
                            switch (mPhotoType){
                                case TYPE_ID_CARD:
                                    mIdCardImageView.setImageBitmap(newAvatar);
                                    break;

                                case TYPE_STUDENT_CARD:
                                    mStudentCardImageView.setImageBitmap(newAvatar);
                                    break;

                                case TYPE_AVATAR:
                                    mAvatarImageView.setImageBitmap(newAvatar);
                                    break;

                                default:
                                    break;
                            }

                            uploadAvatar();
                        } else {
                            Toast.makeText(mContext, "系统裁剪出现异常，请重试", Toast.LENGTH_SHORT).show();
                            tryingAnotherCropMethod = true;
                            startPhotoZoom(imageCaptureUri);
                        }
                    }
                } else {
                    newAvatar = BitmapUtils.getBitmapFromUri(mContext, imageCaptureUri);
                    switch (mPhotoType){
                        case TYPE_ID_CARD:
                            mIdCardImageView.setImageBitmap(newAvatar);
                            break;

                        case TYPE_STUDENT_CARD:
                            mStudentCardImageView.setImageBitmap(newAvatar);
                            break;

                        case TYPE_AVATAR:
                            mAvatarImageView.setImageBitmap(newAvatar);
                            break;

                        default:
                            break;
                    }

                    uploadAvatar();
                }

                break;

            default:
                break;
        }
    }

    /**
     * 更新头像
     */
    private void uploadAvatar() {
        //上传头像
        if (newAvatar != null) {
            String fileName = "tempAvatar.jpg";
            BitmapUtils.saveBitmap(newAvatar, fileName);
            String path = Constants.Path.COMPLETE_PATH + fileName;
            final File file = new File(path);
            LogUtils.i("保存裁剪头像后--->path = " + path);
            LogUtils.i("保存裁剪头像后--->file.exists() = " + file.exists());

            //Todo
//            String token = mSpUtils.getValue(Constant.Config.QN_TOKEN, null);
//            if (token == null || token.length() == 0) {
//                showToast("token为空！");
//            } else {
//                LogUtils.i("上传头像token = " + token);
//                upLoadImages(token, file);
//            }
        }
    }

    /**
     * 上传图片，设为头像
     *
     * @param token 七牛申请的token
     * @param file  裁剪后的头像文件
     */
    private void upLoadImages(String token, File file) {
        if (file != null) {
            UploadManager mUploadManager = new UploadManager();
            mUploadManager.put(file, System.currentTimeMillis() + "", token, new UpCompletionHandler() {
                @Override
                public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {

                    if (responseInfo.isOK()) {
                        try {
                            //Todo
//                            final String avatarURL = Constant.Config.QINIU_URL + jsonObject.getString("key");
//                            mUser.setAvatar(avatarURL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        toast("上传失败" + responseInfo.error);
                        LogUtils.i(mTAG, "上传失败" + responseInfo.error);
                    }
                }
            }, new UploadOptions(null, null, false, new UpProgressHandler() {
                @Override
                public void progress(String s, double v) {

                }
            }, null));
        }
    }

    /**
     * 裁剪图片方法实现
     */
    public void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Toast.makeText(mContext, "您的手机不支持截图", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            LogUtils.i("uri : " + uri.getPath());
            imageCaptureUri = uri;
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            intent.putExtra("crop", "true");
            if(mPhotoType != TYPE_AVATAR) {
                // aspectX aspectY 是宽高的比例
                intent.putExtra("aspectX", IMAGE_WIDTH_RATE);
                intent.putExtra("aspectY", IMAGE_HEIGHT_RATE);
            }else{
                intent.putExtra("aspectX", IMAGE_WIDTH_RATE);
                intent.putExtra("aspectY", IMAGE_WIDTH_RATE);
            }
//            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", UPLOAD_AVATAR_SIZE);
            intent.putExtra("outputY", UPLOAD_AVATAR_SIZE);

            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("scale", true);
            if (!tryingAnotherCropMethod) {
                intent.putExtra("return-data", true);
            } else {
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            }
            startActivityForResult(intent, ACTION_CROP);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "您的手机不支持截图", Toast.LENGTH_SHORT).show();
        }
    }
}
