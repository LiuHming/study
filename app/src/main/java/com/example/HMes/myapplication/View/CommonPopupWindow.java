package com.example.HMes.myapplication.View;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.SizeUtils;

public class CommonPopupWindow extends PopupWindow {

    private CommonPopupWindow(Builder builder) {
        super(builder.context);
        builder.view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        setContentView(builder.view);
        setHeight(builder.height == 0?ViewGroup.LayoutParams.WRAP_CONTENT:builder.height);
        setWidth(builder.width == 0?ViewGroup.LayoutParams.WRAP_CONTENT:builder.width);
        if (builder.cancelTouchout) {
            setBackgroundDrawable(new ColorDrawable(0x00000000));
            //设置透明背景
            setOutsideTouchable(builder.cancelTouchout);
            // 设置outside可点击
            }
            setFocusable(builder.isFocusable);
        setTouchable(builder.isTouchable);
        if(builder.animStyle != 0){
            setAnimationStyle(builder.animStyle);
        }
    }
    public static final class Builder {
        private Context context;
        private int height, width;
        private boolean cancelTouchout;
        private boolean isFocusable = true;
        private boolean isTouchable = true;
        private View view;
        private int animStyle;
        public Builder(Context context) {
            this.context = context;
        }
        public Builder view(int resView) {
            view = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }
        public Builder view(View resVew){
            view = resVew;
            return this;
        }
        public Builder heightpx(int val)
        {
            height = val;
            return this;
        }
        public Builder widthpx(int val) {
            width = val;
            return this;
        }
        public Builder heightdp(int val) {
            height = SizeUtils.dp2px( val);
            return this;
        }
        public Builder widthdp(int val) {
            width = SizeUtils.dp2px( val);
            return this;
        }
        public Builder heightDimenRes(int dimenRes) {
            height = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }
        public Builder widthDimenRes(int dimenRes) {
            width = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }
        public Builder cancelTouchout(boolean val) {
            cancelTouchout = val;
            return this;
        }
        public Builder isFocusable(boolean val) {
            isFocusable = val;
            return this;
        }
        public Builder isTouchable(boolean val) {
            isTouchable = val;
            return this;
        }
        public Builder animStyle(int val){
            animStyle = val;
            return this;
        }
        public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }
        public CommonPopupWindow build() {
            return new CommonPopupWindow(this);
        }
    }
    @Override
    public int getWidth() {
        return getContentView().getMeasuredWidth();
    }

}
