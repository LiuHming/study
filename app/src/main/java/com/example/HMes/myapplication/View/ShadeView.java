package com.example.HMes.myapplication.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HMes.myapplication.R;

public class ShadeView extends FrameLayout {
    /**
     * 一般状态下的图标
     */
    private ImageView iv_normalIcon;
    /**
     * 选中状态下的图标
     */
    private ImageView iv_selectIcon;
    /**
     * 一般状态下底部 TextView
     */
    private TextView tv_normalLabel;
    /**
     * 选中状态下底部 TextView
     */
    private TextView tv_selectLabel;
    /**
     * 选中状态下的文字颜色
     */
    private int selectTextColor;
    /**
     * 底部文字默认大小(sp)
     */
    private final int DEFAULT_TEXT_SIZE = 6;
    /**
     * 一般状态下底部文字默认颜色
     */
    private final String DEFAULT_NORMAL_TEXT_COLOR = "#515151";
    /**
     * 选中状态下底部文字默认颜色
     */
    private final String DEFAULT_SELECT_TEXT_COLOR = "#0288d1";
    private float alpha;
    public ShadeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        //获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadeTabView, 0, 0);
        BitmapDrawable normalIcon = (BitmapDrawable) typedArray.getDrawable(R.styleable.ShadeTabView_normalIcon);
        BitmapDrawable selectIcon = (BitmapDrawable) typedArray.getDrawable(R.styleable.ShadeTabView_selectIcon);
        String tab = typedArray.getString(R.styleable.ShadeTabView_tab);
        float textSize = typedArray.getDimension(R.styleable.ShadeTabView_textSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE, getResources().getDisplayMetrics()));
        int normalTextColor = typedArray.getColor(R.styleable.ShadeTabView_normalTextColor, Color.parseColor(DEFAULT_NORMAL_TEXT_COLOR));
        selectTextColor = typedArray.getColor(R.styleable.ShadeTabView_selectTextColor, Color.parseColor(DEFAULT_SELECT_TEXT_COLOR));
        //资源回收
        typedArray.recycle();
        //属性设置
        iv_normalIcon.setImageDrawable(normalIcon);
        iv_selectIcon.setImageDrawable(selectIcon);
        tv_normalLabel.setText(tab);
        tv_normalLabel.setTextSize(textSize);
        tv_normalLabel.setTextColor(normalTextColor);
        tv_selectLabel.setText(tab);
        tv_selectLabel.setTextSize(textSize);
        tv_selectLabel.setTextColor(selectTextColor);
        setIconAlpha(1);
    }
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.tabview, this);
        iv_normalIcon = (ImageView) findViewById(R.id.iv_normal_icon);
        iv_selectIcon = (ImageView) findViewById(R.id.iv_select_icon);
        tv_normalLabel = (TextView) findViewById(R.id.tv_normal_label);
        tv_selectLabel = (TextView) findViewById(R.id.tv_select_label);
    }
    /**
     * 设置上下两层图片的透明度,alpha 值越小,图片的颜色越倾向于带有颜色的图层
     *
     * @param alpha 透明度
     */
    public void setIconAlpha(float alpha) {
// setAlpha(float) 0.0f~1.0f 数值越大越不透明
        iv_selectIcon.setAlpha(1 - alpha);
        iv_normalIcon.setAlpha(alpha);
        tv_normalLabel.setAlpha(alpha);
        tv_selectLabel.setAlpha(1 - alpha);
        this.alpha = alpha;
    }
    private static final String STATE_INSTANCE = "STATE_INSTANCE";
    private static final String STATE_ALPHA = "STATE_ALPHA";
    /**
     * 保存状态
     *
     * @return Parcelable
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putFloat(STATE_ALPHA, alpha);
        return bundle;
    }
    /**
     * 恢复状态
     *
     * @param parcelable Parcelable
     */
    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
        setIconAlpha(bundle.getFloat(STATE_ALPHA));
    }
}
