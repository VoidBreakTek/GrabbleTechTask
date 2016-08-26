package uk.co.breaktek.grabbletechtask;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

/**
 */
class ProductImageAdapter extends PagerAdapter {
    private Context mContext;
    private List<Integer> mImageResourceIds = Arrays.asList(R.drawable.photo_1, R.drawable.photo_2, R.drawable.photo_3);
    private OnItemClickListener mListener;
    private float mPageWidthScale = 1f;

    ProductImageAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_product_image_view_pager_page, collection, false);
        ImageView imageView = (ImageView) layout.findViewById(R.id.view_product_image_view_pager_page_imageview);
        imageView.setImageDrawable(ContextCompat.getDrawable(mContext, mImageResourceIds.get(position)));
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.itemClicked();
            }
        });
        collection.addView(layout);
        return layout;
    }

    void setPageWidthScale(float pageWidthScale) {
        this.mPageWidthScale = pageWidthScale;
    }

    @Override
    public float getPageWidth(int position) {
        return mPageWidthScale;
    }

    interface OnItemClickListener {
        void itemClicked();
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
