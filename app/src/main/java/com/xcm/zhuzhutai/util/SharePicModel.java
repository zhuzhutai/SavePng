package com.xcm.zhuzhutai.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.homg.picgen.GenerateModel;
import com.homg.picgen.GeneratePictureManager;
import com.xcm.zhuzhutai.R;

public class SharePicModel extends GenerateModel {
    private View mSharePicView;
    private int mAvatarResId;
//    private ShopListDetail bean;

    public SharePicModel(ViewGroup rootView, Object url) {
        super(rootView);
//        this.bean = url;
    }

    @Override
    protected void startPrepare(final GeneratePictureManager.OnGenerateListener listener) throws Exception {
        mSharePicView = LayoutInflater.from(mContext).inflate(R.layout.share_goods_detail, mRootView, false);
//        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), BitmapFactory.decodeResource(mContext.getResources(), mAvatarResId));
//        circularBitmapDrawable.setCircular(true);
//        mTitleAvatarIv.setImageDrawable(circularBitmapDrawable);

//        TextView title = mSharePicView.findViewById(R.id.share_title);
//        TextView tv_price_real = mSharePicView.findViewById(R.id.tv_price_real);
//        TextView price = mSharePicView.findViewById(R.id.tv_price);
//        TextView tv_amount = mSharePicView.findViewById(R.id.tv_amount);
        final ImageView img = mSharePicView.findViewById(R.id.iv_shop);
//        TextView realPrice = mSharePicView.findViewById(R.id.text_price);
//        ImageView imgQr = mSharePicView.findViewById(R.id.img_qr);

        //对布局进行填充
//        title.setText(bean.goodsName);
//        price.setText("原价¥" + Strings.getRate(bean.selfPrice));
        double coup = 0;
//        List<ShopListDetail.CouponsBean> coupons = bean.coupons;
//        if (coupons != null && coupons.size() > 0) {
//            coup = coupons.get(0).amount;
//            tv_amount.setText(Strings.getRate(coup) + "元券");
//            tv_amount.setVisibility(View.VISIBLE);
//            tv_price_real.setVisibility(View.VISIBLE);
//        } else {
//            coup = 0;
//            tv_amount.setVisibility(View.GONE);
//            tv_price_real.setVisibility(View.GONE);
//        }
//        double realDPrice = bean.selfPrice - coup;
//        realPrice.setText("券后价 \n ¥ ");


//        tv_price_real.setText("券后 ¥ ");

        RequestOptions options = new RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .disallowHardwareConfig();
        Glide.with(mContext)
                .asBitmap()
                .apply(options)
                .load("https://img.alicdn.com/tfscom/i1/725677994/O1CN01RLsv1c28vIjE8FD9M_!!0-item_pic.jpg")
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        img.setImageDrawable(drawable);
                        prepared(listener);
                    }
                });

//        String appDownloadUrl = (String) SPUtils.get("App_downloadUrl", "www.哒哒哒.com");
//        imgQr.setImageBitmap(QRCode.createQRCodeWithLogo6(appDownloadUrl, 500, ImageLoadGlide.drawableToBitmap(mContext.getResources().getDrawable(R.mipmap.ic_launcher))));

    }

    @Override
    public View getView() {
        return mSharePicView;
    }

    public void setAvatarResId(int avatarResId) {
        mAvatarResId = avatarResId;
    }
}
