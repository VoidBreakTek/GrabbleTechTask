package uk.co.breaktek.grabbletechtask;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 */
class ReweightLayoutAnimation extends Animation {
    private final float mStartWeight;
    private final float mDeltaWeight;
    private final View mWeightedView;

    ReweightLayoutAnimation(float startWeight, float endWeight, View weightedView) {
        mStartWeight = startWeight;
        mDeltaWeight = endWeight - startWeight;
        mWeightedView = weightedView;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mWeightedView.getLayoutParams();
        params.weight = (mStartWeight + (mDeltaWeight * interpolatedTime));
        mWeightedView.setLayoutParams(params);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}