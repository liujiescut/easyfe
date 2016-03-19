package com.scut.easyfe.ui.customView.SimpleHUD;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scut.easyfe.R;


class SimpleHUDDialog extends Dialog {

    private String message;
    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView messageView;
    private boolean showProgressBar;

	public SimpleHUDDialog(Context context, int theme) {
		super(context, theme);
	}
	
	public static SimpleHUDDialog createDialog(Context context) {
		SimpleHUDDialog dialog = new SimpleHUDDialog(context, R.style.SimpleHUD);
		dialog.setContentView(R.layout.simplehud);
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return dialog;
	}

	public void setMessage(String message) {
        this.message = message;
		messageView = (TextView)findViewById(R.id.simplehud_message);
        messageView.setText(message);
	}
	
	public void setImage(int resId) {
		imageView = (ImageView)findViewById(R.id.simplehud_image);
        if (resId > 0)
        imageView.setImageResource(resId);
	}

    public void showProgressBar(boolean show) {
        showProgressBar = show;
        progressBar = (ProgressBar)findViewById(R.id.simplehud_progress);
    }

    @Override
    public void show() {
        super.show();
        progressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
        imageView.setVisibility(showProgressBar ? View.GONE : View.VISIBLE);

        if (message == null || message.length() == 0)
            messageView.setVisibility(View.GONE);
        else
            messageView.setVisibility(View.VISIBLE);
    }
}
