package com.xcm.zhuzhutai.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.xcm.zhuzhutai.R;

public abstract class BaseActivity extends AppCompatActivity {

    public Activity mContext;
    private ProgressDialog mainWaitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        getIntentBuilder();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(initLayout());
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoading();
    }

    public void showLoading() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        if (mainWaitDialog == null) {
            mainWaitDialog = new ProgressDialog(mContext);
//            pd.setTitle("");
            mainWaitDialog.setMessage("正在加载...");
            mainWaitDialog.setCancelable(true);
            mainWaitDialog.setCanceledOnTouchOutside(false);
            mainWaitDialog.show();
        }
        mainWaitDialog.setCanceledOnTouchOutside(false);
        mainWaitDialog.show();
    }

    public void hideLoading() {
        if (mainWaitDialog != null) {
            mainWaitDialog.dismiss();
        }
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected void getIntentBuilder() {
    }

    public Object getBasicValue(String key, Object defaultObject) {
        if (getIntent() == null)
            return defaultObject;
        if (defaultObject instanceof String) {
            return getIntent().getStringExtra(key);
        } else if (defaultObject instanceof Integer) {
            return getIntent().getIntExtra(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return getIntent().getBooleanExtra(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return getIntent().getFloatExtra(key, (Float) defaultObject);
        } else if (defaultObject instanceof Double) {
            return getIntent().getDoubleExtra(key, (Double) defaultObject);
        } else if (defaultObject instanceof Long) {
            return getIntent().getLongExtra(key, (Long) defaultObject);
        }
        return defaultObject;
    }

    public void showDialog(Bitmap bitmap) {
        View view = mContext.getLayoutInflater().inflate(R.layout.dialog_img, null);
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉蓝色的标题线
        Window window = dialog.getWindow();

        window.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        if (!mContext.isFinishing()) {
            dialog.show();
        }
        //设置宽高的代码 需要放在show后面才有效
        window.setBackgroundDrawable(new ColorDrawable(0));
        Display dis = mContext.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (dis.getWidth() * 0.80);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        ImageView img = view.findViewById(R.id.img);
        img.setImageBitmap(bitmap);
    }
}
