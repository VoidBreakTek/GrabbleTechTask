package uk.co.breaktek.grabbletechtask;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import static uk.co.breaktek.grabbletechtask.ScaledAnimationDurationUtil.getScaledAnimSpeed;

/**
 */
class FadeAnimationUtil {
    static Animation getFadeInAnimation(float duration, final View view) {
        Animation fadeAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.setAlpha(1f * interpolatedTime);
            }
        };
        fadeAnimation.setInterpolator(new AccelerateInterpolator());
        fadeAnimation.setDuration(getScaledAnimSpeed(duration));
        fadeAnimation.setFillAfter(true);
        return fadeAnimation;
    }

    static Animation getFadeOutAnimation(float duration, final View view) {
        Animation fadeAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.setAlpha(1f - (1f * interpolatedTime));
            }
        };
        fadeAnimation.setDuration(getScaledAnimSpeed(duration));
        fadeAnimation.setInterpolator(new AccelerateInterpolator());
        fadeAnimation.setFillAfter(true);
        return fadeAnimation;
    }
}
