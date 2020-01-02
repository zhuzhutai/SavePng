package com.xcm.zhuzhutai.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.xcm.zhuzhutai.R;

import java.util.List;

public class BannerShareAdapter extends PagerAdapter {

    private final int screenWidth;
    private List<Integer> list;
    private Context context;
    private SparseArray<View> mPageCache = new SparseArray<View>();
    private View mCurrentView;

    public BannerShareAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
        screenWidth = UIUtil.getScreenWidth(context) - 80;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View page = mPageCache.get(position);
        if (page == null) {
            page = LayoutInflater.from(context).inflate(R.layout.banner_share, container, false);
            ImageView iv = page.findViewById(R.id.iv_banner);
            TextView textView = page.findViewById(R.id.textView);
            textView.setText("邀请码 1245");
            iv.setImageResource(list.get(position));
//            ImageView img_qr = page.findViewById(R.id.img_qr);
//            String appDownloadUrl = (String) SPUtils.get("App_downloadUrl", "www.哒哒哒.com");
//            img_qr.setImageBitmap(QRCode.createQRCodeWithLogo6(appDownloadUrl, 500, ImageLoadGlide.drawableToBitmap(context.getResources().getDrawable(R.mipmap.ic_launcher))));
        }
        mPageCache.append(position, page);
        container.addView(page);

        ViewGroup.LayoutParams layoutParams = page.getLayoutParams();

        layoutParams.width = screenWidth;
        layoutParams.height = screenWidth * 400 / 275;
        page.setLayoutParams(layoutParams);

        return page;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        mCurrentView = (View) object;
    }

    public View getCur() {
        return mCurrentView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}