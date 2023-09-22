package com.example.imageversion8;

import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

public class ZoomPageTransformer implements ViewPager2.PageTransformer {
    private static final float MIN_SCALE = 0.85f;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 1) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float verticalMargin = pageHeight * (1 - scaleFactor) / 2;
            float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;

            if (position < 0) {
                page.setTranslationX(horizontalMargin - verticalMargin / 2);
            } else {
                page.setTranslationX(-horizontalMargin + verticalMargin / 2);
            }

            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setAlpha(MIN_SCALE + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_SCALE));
        } else {
            page.setAlpha(0);
        }
    }
}
