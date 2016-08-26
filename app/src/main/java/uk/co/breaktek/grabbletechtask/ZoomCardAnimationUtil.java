package uk.co.breaktek.grabbletechtask;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import static uk.co.breaktek.grabbletechtask.ScaledAnimationDurationUtil.getScaledAnimSpeed;

/**
 */
class ZoomCardAnimationUtil {
    private View mCardView;
    private int cardViewInitialPaddingTop;
    private int cardViewInitialMarginTop;
    private int cardViewInitialMarginBottom;
    private int cardViewInitialMarginLeft;
    private int cardViewInitialMarginRight;

    private boolean mIsZoomedIn = false;

    void zoomIn(float durationScale, final ZoomAnimationListener listener) {
        if (!mIsZoomedIn) {
            ZoomCardInAnimation zoomIn = new ZoomCardInAnimation();
            zoomIn.setDuration(getScaledAnimSpeed(durationScale));
            zoomIn.setFillAfter(true);
            zoomIn.setInterpolator(new AccelerateDecelerateInterpolator());
            zoomIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mIsZoomedIn = true;
                    listener.onAnimationFinished();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mCardView.startAnimation(zoomIn);
        }
    }

    void zoomOut(float durationScale, final ZoomAnimationListener listener) {
        if (mIsZoomedIn) {
            ZoomCardOutAnimation zoomOut = new ZoomCardOutAnimation();
            zoomOut.setDuration(getScaledAnimSpeed(durationScale));
            zoomOut.setFillAfter(true);
            zoomOut.setInterpolator(new AccelerateDecelerateInterpolator());
            zoomOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mIsZoomedIn = false;
                    listener.onAnimationFinished();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mCardView.startAnimation(zoomOut);
        }
    }

    void initialize(View cardView) {
        mCardView = cardView;
        RelativeLayout.LayoutParams cardViewInitialLayoutParams = (RelativeLayout.LayoutParams) mCardView.getLayoutParams();
        cardViewInitialPaddingTop = mCardView.getPaddingTop();
        cardViewInitialMarginTop = cardViewInitialLayoutParams.topMargin;
        cardViewInitialMarginBottom = cardViewInitialLayoutParams.bottomMargin;
        cardViewInitialMarginLeft = cardViewInitialLayoutParams.leftMargin;
        cardViewInitialMarginRight = cardViewInitialLayoutParams.rightMargin;
    }

    private class ZoomCardInAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int topPadding = (int) (cardViewInitialPaddingTop - (interpolatedTime * cardViewInitialPaddingTop));
            mCardView.setPadding(0, topPadding, 0, 0);
            RelativeLayout.LayoutParams cardParams = (RelativeLayout.LayoutParams) mCardView.getLayoutParams();
            cardParams.topMargin = (int) (cardViewInitialMarginTop - (interpolatedTime * cardViewInitialMarginTop));
            cardParams.bottomMargin = (int) (cardViewInitialMarginBottom - (interpolatedTime * cardViewInitialMarginBottom));
            cardParams.leftMargin = (int) (cardViewInitialMarginLeft - (interpolatedTime * cardViewInitialMarginLeft));
            cardParams.rightMargin = (int) (cardViewInitialMarginRight - (interpolatedTime * cardViewInitialMarginRight));
            mCardView.requestLayout();
        }
    }

    private class ZoomCardOutAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int topPadding = (int) (0 + (interpolatedTime * cardViewInitialPaddingTop));
            mCardView.setPadding(0, topPadding, 0, 0);
            RelativeLayout.LayoutParams cardParams = (RelativeLayout.LayoutParams) mCardView.getLayoutParams();
            cardParams.topMargin = (int) (0 + (interpolatedTime * cardViewInitialMarginTop));
            cardParams.bottomMargin = (int) (0 + (interpolatedTime * cardViewInitialMarginBottom));
            cardParams.leftMargin = (int) (0 + (interpolatedTime * cardViewInitialMarginLeft));
            cardParams.rightMargin = (int) (0 + (interpolatedTime * cardViewInitialMarginRight));
            mCardView.requestLayout();
        }
    }

    interface ZoomAnimationListener {
        void onAnimationFinished();
    }
}
