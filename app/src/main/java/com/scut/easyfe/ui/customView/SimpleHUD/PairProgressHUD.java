package com.scut.easyfe.ui.customView.SimpleHUD;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.scut.easyfe.R;


public class PairProgressHUD {
	
	private static SimpleHUDDialog dialog;
	private static Context context;
	
	public static void show(Context context) {
		dismiss();
		setDialog(context, null, true, 0, false);
		if(dialog!=null) dialog.show();
	}

	/**
	 *  显示加载框
	 * @param context 上下文
	 * @param text    显示文本
	 * @param mills   多少秒后关闭
	 */
	public static void showLoading(Context context, String text, long mills){
		dismiss();
		dismissAfter(mills);
		setDialog(context, text, true, 0, false);
		if(dialog!=null) dialog.show();
	}

    public static void showLoading(Context context,String text){
		showLoading(context, text, false);
    }

	public static void showLoading(Context context, String text, boolean cancelable) {
		showLoading(context, text, cancelable, null);
	}
	public static void showLoading(Context context, String text, boolean cancelable, DialogInterface.OnDismissListener listener){
		dismiss();
		setDialog(context, text, true, 0, cancelable, listener);
		if(dialog!=null) dialog.show();
	}

    public static void showInfo(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showSuccess(Context context, String msg) {
        dismiss();

        if (msg != null && msg.length() > 0) {
            setDialog(context, msg, false, R.mipmap.simplehud_success, false);
            if(dialog!=null) {
                dialog.show();
                dismissAfter(1000);
            }
        }
    }
	
	public static void showError(Context context, String msg) {
        dismiss();
        setDialog(context, msg, false, R.mipmap.simplehud_error, true);
        if (dialog != null) {
            dialog.show();
            dismissAfter(2000);
        }
    }

	private static void setDialog(Context ctx, String msg, boolean showProgressBar,  int resId, boolean cancelable) {
		setDialog(ctx, msg, showProgressBar, resId, cancelable, null);
	}

    private static void setDialog(Context ctx, String msg, boolean showProgressBar,  int resId, boolean cancelable, DialogInterface.OnDismissListener onDismissListener) {
		context = ctx;

		if(!isContextValid())
			return;
		
		dialog = SimpleHUDDialog.createDialog(ctx);
		dialog.setMessage(msg);
        dialog.showProgressBar(showProgressBar);
		dialog.setImage(resId);
		dialog.setCanceledOnTouchOutside(cancelable);
		dialog.setCancelable(cancelable);		// back键是否可dimiss对话框

		if(onDismissListener != null){
			dialog.setOnDismissListener(onDismissListener);
		}
	}

	public static void dismiss() {
		if(isContextValid() && dialog!=null && dialog.isShowing())
			dialog.dismiss();
		dialog = null;
	}

    public static void dismiss(String resultMessage) {
        dismiss();
        if (resultMessage != null && resultMessage.length() > 0)
            PairProgressHUD.showInfo(context, resultMessage);
    }

	/**
	 * 计时关闭对话框
	 * 
	 */
	private static void dismissAfter(final long millisecond) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(millisecond);
					handler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	

	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what==0)
				dismiss();
		};
	};
	

	/**
	 * 判断parent view是否还存在
	 * 若不存在不能调用dismis，或setDialog等方法
	 * @return
	 */
	private static boolean isContextValid() {
		if(context==null)
			return false;
		if(context instanceof Activity) {
			Activity act = (Activity)context;
			if(act.isFinishing())
				return false;
		}
		return true;
	}
}
