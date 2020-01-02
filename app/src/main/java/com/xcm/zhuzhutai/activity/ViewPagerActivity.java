package com.xcm.zhuzhutai.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.xcm.zhuzhutai.R;
import com.xcm.zhuzhutai.util.BannerShareAdapter;
import com.xcm.zhuzhutai.util.BaseActivity;
import com.xcm.zhuzhutai.util.UIUtil;

import java.util.ArrayList;


public class ViewPagerActivity extends BaseActivity {

    ViewPager viewPager;

    private BannerShareAdapter adater;
    private ArrayList<Integer> list;
    private int screenWidth;
    private int margin;

    @Override
    protected int initLayout() {
        return R.layout.activity_share;
    }

    @Override
    protected void initView() {

        viewPager = findViewById(R.id.viewpager);
        findViewById(R.id.tv_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager myClipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                myClip = ClipData.newPlainText("text", "\n" +
                        "邀请您加入荟店，自动搜索淘宝天猫优惠券！先领券，再购物，更划算！\n" +
                        "-------------\n" +
                        "下载链接：******************\n" +
                        "-------------\n" +
                        "复制这条信息∞JWC7FGC∞\n" +
                        "打开荟店，注册领取优惠券\n");
                if (myClipboard != null) {
                    myClipboard.setPrimaryClip(myClip);
                    Toast.makeText(ViewPagerActivity.this, "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSaveToImage(adater.getCur());
            }
        });

        margin = UIUtil.dip2px(mContext, 50);
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        screenWidth = UIUtil.getScreenWidth(mContext) - margin;
        layoutParams.width = screenWidth;
        layoutParams.height = screenWidth * 400 / 275;
        viewPager.setLayoutParams(layoutParams);

        viewPager.setPageMargin(-margin);
        viewPager.setOffscreenPageLimit(10);
        list = new ArrayList<>();
        list.add(R.mipmap.posters_pic2);
        list.add(R.mipmap.posters_pic3);
        list.add(R.mipmap.posters_pic4);
        list.add(R.mipmap.posters_pic5);
        adater = new BannerShareAdapter(this, list);
        viewPager.setAdapter(adater);
        viewPager.setPageTransformer(false, new AlphaTransformer());
        viewPager.setCurrentItem(1);
    }

    class AlphaTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.70f;
        private static final float MIN_ALPHA = 0.9f;

        @Override
        public void transformPage(View page, float position) {
            if (position < -1 || position > 1) {
                page.setAlpha(MIN_ALPHA);
                page.setScaleX(MIN_SCALE);
                page.setScaleY(MIN_SCALE);
            } else if (position <= 1) { // [-1,1]
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                if (position < 0) {
                    float scaleX = 1 + 0.3f * position;
                    page.setScaleX(scaleX);
                    page.setScaleY(scaleX);
                } else {
                    float scaleX = 1 - 0.3f * position;
                    page.setScaleX(scaleX);
                    page.setScaleY(scaleX);
                }
                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            }
        }
    }

    /**
     * 把view转成图片
     *
     * @param view
     */
    private void viewSaveToImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);
        view.buildDrawingCache();
        Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(drawingCache, margin / 3, 0, screenWidth - margin / 3 - margin / 3, screenWidth * 400 / 275);
        showDialog(bitmap);
    }

}
