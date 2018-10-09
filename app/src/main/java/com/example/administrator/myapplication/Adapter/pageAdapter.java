package com.example.administrator.myapplication.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class pageAdapter extends PagerAdapter {

    private List<ImageView> mlist;

    public pageAdapter(List<ImageView> list){
        mlist = list;
    }

    @NonNull
    @Override
    public ImageView instantiateItem( ViewGroup container, int position) {
        ImageView view = mlist.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object) {
        container.removeView(mlist.get(position));
    }
}
