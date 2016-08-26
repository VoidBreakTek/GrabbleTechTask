package uk.co.breaktek.grabbletechtask;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.activity_main_toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_main_content_layout) RelativeLayout mCardView;
    @BindView(R.id.activity_main_root_layout) RelativeLayout mRootRelativeLayout;
    @BindView(R.id.activity_main_cardview_name_desc_layout) LinearLayout mContentLayout;
    @BindView(R.id.activity_main_cardview_image_desc_layout) LinearLayout mImageAndContentLayout;
    @BindView(R.id.activity_main_cardview_name_textview) TextView mNameTextView;
    @BindView(R.id.activity_main_cardview_description_textview) TextView mDescriptionTextView;
    @BindView(R.id.check_view) View mCheckView;
    @BindView(R.id.activity_main_card_bottom_buy_button) View mBuyButton;
    @BindView(R.id.activity_main_card_bottom_grab_button) View mGrabButton;
    @BindView(R.id.activity_main_card_bottom_select_size_view) View mSelectSizeTextView;
    @BindView(R.id.activity_main_card_bottom_long_description_textview) View mLongDescriptionTextView;
    @BindView(R.id.activity_main_cardview_closed_price_button) Button mPriceButton;
    @BindView(R.id.viewpager) ViewPager mViewPager;

    private boolean mIsCardExpanded = false;
    private ProductImageAdapter mViewPagerAdapter;
    private MainActivityAnimator mMainActivityAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initLayoutListenerForZoomUtil();
        initToolbar();
        mMainActivityAnimator = new MainActivityAnimator(this);
        initViewPager();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        setTitle("Best of Investment Pieces");
    }

    private void initLayoutListenerForZoomUtil() {
        ViewTreeObserver observer = mRootRelativeLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mMainActivityAnimator.initializeZoomAnimationUtil(mCardView);
                mRootRelativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initViewPager() {
        mViewPager.setAdapter(getViewPagerAdapter());
    }

    ProductImageAdapter getViewPagerAdapter() {
        if (mViewPagerAdapter == null) {
            mViewPagerAdapter = new ProductImageAdapter(this, new ProductImageAdapter.OnItemClickListener() {
                @Override
                public void itemClicked() {
                    mMainActivityAnimator.animateOpen();
                }
            });
        }
        return mViewPagerAdapter;
    }

    @Override
    public void onBackPressed() {
        if (mIsCardExpanded) {
            mMainActivityAnimator.animateClose();
        } else {
            super.onBackPressed();
        }
    }

    void toggleIsCardExpanded() {
        mIsCardExpanded = !mIsCardExpanded;
    }

    boolean isCardExpanded() {
        return mIsCardExpanded;
    }
}
