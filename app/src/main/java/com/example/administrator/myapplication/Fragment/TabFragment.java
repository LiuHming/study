package com.example.administrator.myapplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment {

    public static TabFragment newInstance(Context context,String string) {
        TabFragment tabFragment = new TabFragment();
        String mTitle = string;
        TextView textView = new TextView(context);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        textView.setText(mTitle);
        return tabFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String mTitle = "HMail";
        if (getArguments() != null) {
            mTitle = getArguments().getString("Title", "微信");
        }
        TextView textView = new TextView(getActivity());
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        textView.setText(mTitle);
        return textView;
    }

}
