package com.example.HMes.myapplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment {

    private static String mtitle;

    public static TabFragment newInstance(Context context,String string) {
        TabFragment tabFragment = new TabFragment();
        mtitle = string;
        return tabFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(mtitle);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

}
