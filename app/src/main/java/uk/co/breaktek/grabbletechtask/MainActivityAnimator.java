package uk.co.breaktek.grabbletechtask;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static uk.co.breaktek.grabbletechtask.FadeAnimationUtil.getFadeInAnimation;
import static uk.co.breaktek.grabbletechtask.FadeAnimationUtil.getFadeOutAnimation;
import static uk.co.breaktek.grabbletechtask.ScaledAnimationDurationUtil.getScaledAnimSpeed;

/**
 */
class MainActivityAnimator {
    private final float OPEN_RESIZE_DURATION = 0.8f;
    private final float CLOSE_RESIZE_DURATION = 1f;
    private final float VIEW_PAGER_WIDTH_OPEN = 200f / 360f;

    private final boolean[] animationInProgress = new boolean[9];

    private MainActivity mMainActivity;
    private ZoomCardAnimationUtil mZoomAnimationUtil;
    private BottomContentSlideAnimationUtil mBottomContentSlideAnimationUtil;

    MainActivityAnimator(MainActivity mainActivity) {
        this.mMainActivity = mainActivity;
        mZoomAnimationUtil = new ZoomCardAnimationUtil();
        mBottomContentSlideAnimationUtil = new BottomContentSlideAnimationUtil(mainActivity);
    }

    void initializeZoomAnimationUtil(View cardView) {
        mZoomAnimationUtil.initialize(cardView);
    }

    void animateOpen() {
        if (!isAnimationInProgress() && !mMainActivity.isCardExpanded()) {
            Arrays.fill(animationInProgress, true);

            float slideFadeDuration = 0.8f;
            float fadeToSlideScale = 1.3f;

            final List<Animation> animations = new ArrayList<>();
            Animation bottomSlideInAnimation = mBottomContentSlideAnimationUtil.getSlideBottomCotainerInAnimation(
                    slideFadeDuration,
                    new AccelerateInterpolator());
            animations.add(bottomSlideInAnimation);

            Animation cardResizeAnimationSet = getCardResizeAnimationSet(
                    true,
                    OPEN_RESIZE_DURATION,
                    new DecelerateInterpolator());
            animations.add(cardResizeAnimationSet);

            Animation selectSizeFadeInAnimation = getFadeInAnimation(slideFadeDuration * fadeToSlideScale, mMainActivity.mSelectSizeTextView);
            animations.add(selectSizeFadeInAnimation);

            Animation buyButtonFadeInAnimation = getFadeInAnimation(slideFadeDuration * fadeToSlideScale, mMainActivity.mBuyButton);
            animations.add(buyButtonFadeInAnimation);

            Animation grabButtonFadeInAnimation = getFadeInAnimation(slideFadeDuration * fadeToSlideScale, mMainActivity.mGrabButton);
            animations.add(grabButtonFadeInAnimation);

            Animation longDescriptionFadeInAnimation = getFadeInAnimation(slideFadeDuration * fadeToSlideScale, mMainActivity.mLongDescriptionTextView);
            animations.add(longDescriptionFadeInAnimation);

            Animation priceButtonFadeOutAnimation = getFadeOutAnimation(0.3f, mMainActivity.mPriceButton);
            animations.add(priceButtonFadeOutAnimation);

            Animator pageWidthScaleDecreaseAnimation = getViewPagerPageWidthScaleAnimation(1f, VIEW_PAGER_WIDTH_OPEN);
            pageWidthScaleDecreaseAnimation.addListener(getAnimatiorFinishedFlagToggleListener(animations.size()));
            pageWidthScaleDecreaseAnimation.setDuration(getScaledAnimSpeed(1f));

            for (Animation animation : animations) {
                animation.setAnimationListener(getAnimationFinishedFlagToggleListener(animations.indexOf(animation)));
            }

            mMainActivity.mCheckView.setVisibility(View.VISIBLE);
            mMainActivity.mCheckView.startAnimation(bottomSlideInAnimation);
            mMainActivity.mSelectSizeTextView.startAnimation(selectSizeFadeInAnimation);
            mMainActivity.mBuyButton.startAnimation(buyButtonFadeInAnimation);
            mMainActivity.mGrabButton.startAnimation(grabButtonFadeInAnimation);
            mMainActivity.mLongDescriptionTextView.startAnimation(longDescriptionFadeInAnimation);
            pageWidthScaleDecreaseAnimation.start();
            mZoomAnimationUtil.zoomIn(OPEN_RESIZE_DURATION, new ZoomCardAnimationUtil.ZoomAnimationListener() {
                @Override
                public void onAnimationFinished() {
                    animationInProgress[animations.size() + 1] = false;
                }
            });
            mMainActivity.mRootRelativeLayout.startAnimation(cardResizeAnimationSet);
            mMainActivity.mPriceButton.startAnimation(priceButtonFadeOutAnimation);
            mMainActivity.toggleIsCardExpanded();
        }
    }

    void animateClose() {
        if (!isAnimationInProgress() && mMainActivity.isCardExpanded()) {
            Arrays.fill(animationInProgress, true);
            final List<Animation> animations = new ArrayList<>();

            Animation bottomSlideOutAnimation = mBottomContentSlideAnimationUtil.getSlideBottomCotainerOutAnimation(
                    0.55f,
                    new AccelerateInterpolator());
            animations.add(bottomSlideOutAnimation);

            Animation cardResizeAnimationSet = getCardResizeAnimationSet(
                    false,
                    CLOSE_RESIZE_DURATION,
                    new AccelerateInterpolator()
            );
            animations.add(cardResizeAnimationSet);

            Animation selectSizeFadeOutAnimation = getFadeOutAnimation(0.5f, mMainActivity.mSelectSizeTextView);
            animations.add(selectSizeFadeOutAnimation);

            Animation buyButtonFadeOutAnimation = getFadeOutAnimation(0.4f, mMainActivity.mBuyButton);
            animations.add(buyButtonFadeOutAnimation);

            Animation grabButtonFadeOutAnimation = getFadeOutAnimation(0.4f, mMainActivity.mGrabButton);
            animations.add(grabButtonFadeOutAnimation);

            Animation longDescriptionFadeOutAnimation = getFadeOutAnimation(0.3f, mMainActivity.mLongDescriptionTextView);
            animations.add(longDescriptionFadeOutAnimation);

            Animation priceButtonFadeInAnimation = getFadeInAnimation(1.5f, mMainActivity.mPriceButton);
            animations.add(priceButtonFadeInAnimation);

            Animator pageWidthScaleIncreaseAnimator = getViewPagerPageWidthScaleAnimation(VIEW_PAGER_WIDTH_OPEN, 1f);
            pageWidthScaleIncreaseAnimator.addListener(getAnimatiorFinishedFlagToggleListener(animations.size()));
            pageWidthScaleIncreaseAnimator.setDuration(getScaledAnimSpeed(1f));

            for (Animation animation : animations) {
                animation.setAnimationListener(getAnimationFinishedFlagToggleListener(animations.indexOf(animation)));
            }

            mMainActivity.mCheckView.setVisibility(View.VISIBLE);
            mMainActivity.mCheckView.startAnimation(bottomSlideOutAnimation);
            mMainActivity.mSelectSizeTextView.startAnimation(selectSizeFadeOutAnimation);
            mMainActivity.mBuyButton.startAnimation(buyButtonFadeOutAnimation);
            mMainActivity.mGrabButton.startAnimation(grabButtonFadeOutAnimation);
            mMainActivity.mLongDescriptionTextView.startAnimation(longDescriptionFadeOutAnimation);
            pageWidthScaleIncreaseAnimator.start();
            mZoomAnimationUtil.zoomOut(CLOSE_RESIZE_DURATION, new ZoomCardAnimationUtil.ZoomAnimationListener() {
                @Override
                public void onAnimationFinished() {
                    animationInProgress[animations.size() + 1] = false;
                }
            });
            mMainActivity.mRootRelativeLayout.startAnimation(cardResizeAnimationSet);
            mMainActivity.mPriceButton.startAnimation(priceButtonFadeInAnimation);
            mMainActivity.toggleIsCardExpanded();
        }
    }

    private Animation getCardResizeAnimationSet(boolean grow, float durationScale, Interpolator interpolator) {
        Animation reweightImageAnimation = new ReweightLayoutAnimation(((LinearLayout.LayoutParams) mMainActivity.mViewPager.getLayoutParams()).weight, grow ? 1 : 7, mMainActivity.mViewPager);
        reweightImageAnimation.setDuration(getScaledAnimSpeed(durationScale));
        reweightImageAnimation.setFillAfter(true);
        reweightImageAnimation.setInterpolator(interpolator);
        return reweightImageAnimation;
    }

    private Animator getViewPagerPageWidthScaleAnimation(final float start, final float end) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mMainActivity.getViewPagerAdapter().setPageWidthScale((float) valueAnimator.getAnimatedValue());
                mMainActivity.mViewPager.setAdapter(mMainActivity.getViewPagerAdapter());
            }
        });
        return valueAnimator;
    }

    private boolean isAnimationInProgress() {
        for (boolean inProgress : animationInProgress) {
            if (inProgress) {
                return true;
            }
        }
        return false;
    }

    private Animation.AnimationListener getAnimationFinishedFlagToggleListener(final int isAnimatingFlagArrayPosition) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationInProgress[isAnimatingFlagArrayPosition] = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private Animator.AnimatorListener getAnimatiorFinishedFlagToggleListener(final int isAnimatingFlagArrayPosition) {
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animationInProgress[isAnimatingFlagArrayPosition] = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };
    }
}
