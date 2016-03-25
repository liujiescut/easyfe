package com.scut.easyfe.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.scut.easyfe.app.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {
    /**
     * 根据uri生成bitmap
     * 注意uri开头不同要进行不同的处理
     * @param context
     * @param uri
     * @return
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri){
        String path = "";
        if(uri.toString().startsWith("content")) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor == null) return null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String s = cursor.getString(column_index);
            cursor.close();
            File file = new File(s);
            path = file.getAbsolutePath();
        }else if(uri.toString().startsWith("file")){
            path = uri.getPath();
        }
        return BitmapFactory.decodeFile(path);
    }

    /**
     * 图片会被保存到应用默认文件夹
     *
     * @param bitmap
     * @param fileName
     */
    public static File saveBitmap(Bitmap bitmap, String fileName) {
        createSDDir(Constants.Path.DIR_WITHOUT_SEPARATE);
        File f = new File(Constants.Path.COMPLETE_PATH + fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);//90 是压缩率，表示压缩10%; 如果不压缩是100，表示压缩率为0
            out.flush();
            out.close();
            return f;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建路径
     *
     * @param dir
     *            不带SDCardRoot和后面的分隔符\的路径
     */
    public static File createSDDir(String dir) {
        File dirFile = new File(Constants.Path.SDCardRoot + dir
                + File.separator);
        LogUtils.i("创建路径结果---->" + dirFile.mkdirs() + "---->"
                + dirFile.getPath());
        return dirFile;
    }

}

