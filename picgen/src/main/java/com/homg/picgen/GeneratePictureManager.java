package com.homg.picgen;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

public class GeneratePictureManager {
    private static GeneratePictureManager sManager;
    private HandlerThread mHandlerThread;
    private Handler mWorkHandler;
    private Handler mMainHandler;


    private GeneratePictureManager() {
        mHandlerThread = new HandlerThread(GeneratePictureManager.class.getSimpleName());
        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper());
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    public static GeneratePictureManager getInstance() {
        if (sManager == null) {
            synchronized (GeneratePictureManager.class) {
                if (sManager == null) {
                    sManager = new GeneratePictureManager();
                }
            }
        }
        return sManager;
    }

    public void generate(GenerateModel generateModel, OnGenerateListener listener) {
        mWorkHandler.post(() -> {
            try {
                generateModel.startPrepare(listener);
            } catch (Exception e) {
                e.printStackTrace();
                if (listener != null) {
                    postResult(() -> {
                        listener.onGenerateFinished(e, null);
                    });
                }
            }
        });
    }

    private void postResult(Runnable runnable) {
        mMainHandler.post(runnable);
    }

    public void prepared(GenerateModel generateModel, OnGenerateListener listener, Context mContext) {
        mWorkHandler.post(() -> {
            View view = generateModel.getView();
            Exception exception = null;
            Bitmap bitmap = null;
            try {
                bitmap = createBitmap(view, mContext);
                String savePath = generateModel.getSavePath();
                if (!TextUtils.isEmpty(savePath)) {
                    if (!BitmapUtil.saveImage(bitmap, savePath)) {
                        exception = new RuntimeException("pic save failed");
                    }
                }
            } catch (Exception e) {
                exception = e;
                e.printStackTrace();
            }
            if (listener != null) {
                final Exception exception1 = exception;
                final Bitmap bitmap1 = bitmap;
                mMainHandler.post(() -> {
                    listener.onGenerateFinished(exception1, bitmap1);
                });
            }
        });
    }

    /**
     * 生成Bitmap
     */
    private Bitmap createBitmap(View view, Context mContext) {

        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();

        // 测量
        view.measure(dm.widthPixels, dm.heightPixels);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        int w = view.getWidth();
        int h = view.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        view.layout(0, 0, w, h);
        view.draw(c);

        return bmp;
    }

    public interface OnGenerateListener {
        void onGenerateFinished(Throwable throwable, Bitmap bitmap);
    }
}
