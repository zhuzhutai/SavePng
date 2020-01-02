package com.xcm.zhuzhutai.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.homg.picgen.GeneratePictureManager;
import com.xcm.zhuzhutai.R;
import com.xcm.zhuzhutai.util.BaseActivity;
import com.xcm.zhuzhutai.util.SharePicModel;

public class MainActivity extends BaseActivity {


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    public void demo_normal(View view) {
        showLoading();
        SharePicModel sharePicModel = new SharePicModel((ViewGroup) getWindow().getDecorView(), null);
        sharePicModel.setAvatarResId(R.mipmap.ic_launcher);
        GeneratePictureManager.getInstance().generate(sharePicModel, new GeneratePictureManager.OnGenerateListener() {
            @Override
            public void onGenerateFinished(Throwable throwable, Bitmap bitmap) {
                if (throwable != null || bitmap == null) {
                    Toast.makeText(mContext, "生成图片失败" + throwable.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "生成图片成功", Toast.LENGTH_SHORT).show();
                    hideLoading();
                    showDialog(bitmap);
                }
            }
        });
    }

    public void demo_viewpager(View view) {
        startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
    }
}
