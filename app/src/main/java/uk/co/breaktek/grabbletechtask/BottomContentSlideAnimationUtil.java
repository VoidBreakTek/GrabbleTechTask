package uk.co.breaktek.grabbletechtask;

import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

import static uk.co.breaktek.grabbletechtask.ScaledAnimationDurationUtil.getScaledAnimSpeed;

/**
 */
class BottomContentSlideAnimationUtil {
    private MainActivity mMainActivity;

    BottomContentSlideAnimationUtil(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    Animation getSlideBottomCotainerInAnimation(float scale, Interpolator interpolator) {
        DisplayMetrics dm = new DisplayMetrics();
        mMainActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int originalPos[] = new int[2];
        mMainActivity.mCheckView.getLocationOnScreen(originalPos);

        int halfPlusNameDescOffset = dm.heightPixels / 2 + getHeightOfNameAndDescription();
        return getBottomContainerSlideAnimation(scale, interpolator, dm.heightPixels, halfPlusNameDescOffset - originalPos[1]);
    }

    Animation getSlideBottomCotainerOutAnimation(float scale, Interpolator interpolator) {
        DisplayMetrics dm = new DisplayMetrics();
        mMainActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int originalPos[] = new int[2];
        mMainActivity.mCheckView.getLocationOnScreen(originalPos);
        int halfPlusNameDescOffset = dm.heightPixels / 2 + getHeightOfNameAndDescription();
        return getBottomContainerSlideAnimation(scale, interpolator, halfPlusNameDescOffset - originalPos[1], dm.heightPixels);
    }

    private Animation getBottomContainerSlideAnimation(float scale, Interpolator interpolator, int yStart, int yEnd) {
        TranslateAnimation slideInAnimation = new TranslateAnimation(0, 0, yStart, yEnd);

        slideInAnimation.setDuration(getScaledAnimSpeed(scale));
        slideInAnimation.setInterpolator(interpolator);
        slideInAnimation.setFillAfter(true);
        return slideInAnimation;
    }

    private int getHeightOfNameAndDescription() {
        return mMainActivity.mNameTextView.getHeight()
                + mMainActivity.mNameTextView.getPaddingTop()
                + mMainActivity.mNameTextView.getPaddingBottom()
                + mMainActivity.mDescriptionTextView.getHeight()
                + mMainActivity.mDescriptionTextView.getPaddingTop()
                + mMainActivity.mDescriptionTextView.getPaddingBottom();
    }
}
