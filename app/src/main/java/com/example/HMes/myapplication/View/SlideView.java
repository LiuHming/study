package com.example.HMes.myapplication.View;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.HMes.myapplication.Interface.onSwipeProgressListener;
import com.example.HMes.myapplication.R;
import com.example.HMes.myapplication.Utils.SizeUtils;

public class SlideView extends ViewGroup {

    private float xIntercept = 0;
    private float yIntercept = 0;
    private float xLast = 0;
    private float yLast = 0;
    private Scroller mScroller;
    private int mScreenWidth;
    private int mScreenHeight;
    private View mMenuView;
    private View mContentView;
    private int mType;
    private int mDragWipeOffset; //侧边拖动的偏移值
    private int mMenuOffset; //菜单距右边的距离
    private boolean isMeasured = false; //是否已经测量过
    private boolean isMenuShowing = false; //是否已经显示了菜单

    private ImageView mBackImageView; //设置动态模糊时候的背景
    private View statusView = null;
    private boolean isTranslate = false; //是否透明
    private boolean isTranslated = false; //是否已经设置过透明
    private onSwipeProgressListener mListener; //滑动监听


    //是否显示菜单
    public boolean isMenuShowing() {
        if (getScrollX() <= 0) {
            isMenuShowing = true;
        } else {
            isMenuShowing = false;
        }
        return isMenuShowing;
    }

    //显示菜单
    public void showMenu() {
        mScroller.startScroll(getScrollX(), 0, 0 - getScrollX(), 0);
        invalidate();
    }

    //显示内容
    public void hideMenu() {
        mScroller.startScroll(getScrollX(), 0, mScreenWidth - mMenuOffset - getScrollX(), 0);
        invalidate();
    }

    public SlideView (Context context) {
        this(context, null);
    }

    public SlideView (Context context, AttributeSet attrs) {
        super(context, attrs);
        initObtainStyledAttr(context, attrs);
        init(context);
    }


    //从资源文件获取数据
    private void initObtainStyledAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlideMenu);
        mType = array.getInteger(R.styleable.SlideMenu_sm_type, 1111);
        mDragWipeOffset = (int) array.getDimension(R.styleable.SlideMenu_sm_dragoffset, SizeUtils.Dp2Px(context, 100));
        mMenuOffset = (int) array.getDimension(R.styleable.SlideMenu_sm_menuoffset, SizeUtils.Dp2Px(context, 50));
        array.recycle();
    }

    //设置全局图片并且沉浸
    public void setFull(Activity activity, int backRes, int headColor, float scale, float startBlur, float endBlur) {
        Bitmap backBitmap = BitmapFactory.decodeResource(getResources(), backRes);
        int color = getResources().getColor(headColor);
        if (backBitmap != null) { //图片背景
            statusView = setColor(activity, Color.TRANSPARENT); //设置透明状态栏
            statusView.setBackgroundColor(color); //设置内容区域状态栏颜色
            isTranslate = true;
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            Drawable backDrawable = null;
            backDrawable = new BitmapDrawable(getResources(),backBitmap);
            decorView.setBackground(backDrawable);
        } else { //颜色背景
            isTranslate = false;
            setBackgroundColor(color); //设置背景
            statusView = setColor(activity, color); //设置状态栏颜色
        } //解决内容区域页面上移问题
        if (!isTranslated) {
            mContentView = getChildAt(1);
            LinearLayout ll = new LinearLayout(activity);
            ll.setLayoutParams(new LinearLayout.LayoutParams(mScreenWidth, mScreenHeight));
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setBackgroundColor(Color.WHITE);
            removeViewAt(1);
            ll.addView(createStatusBarView(activity, Color.TRANSPARENT)); //填充空白界面
            ll.addView(mContentView);
            addView(ll);
            isTranslated = true;
        }

    }

    private View setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            // 添加 statusView 到布局中
            View statusView = createStatusBarView(activity, color);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            if (decorView.getChildAt(decorView.getChildCount() - 1).getId() != Integer.valueOf(1)) {
                decorView.addView(statusView, decorView.getChildCount());
            }
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
            return statusView;
        }
        return null;
    }

    private  View createStatusBarView(Activity activity, int color) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        statusBarView.setId(Integer.valueOf(1));
        return statusBarView;
    }

    private  int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    //添加背景图获取屏幕宽高
    private void init(Context context) {
        mScroller = new Scroller(context);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        boolean intercept = false;
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float xDelta = x - xIntercept;
                float yDelta = y - yIntercept;
                if (mDragWipeOffset == 0 && Math.abs(xDelta) > 20) { //全屏滑动
                    intercept = true;
                    break;
                }
                if (!isMenuShowing()) {
                    if (x >= SizeUtils.Dp2Px(getContext(), mDragWipeOffset)) {
                        return false;
                    }
                }
                if (x + getScrollX() < mScreenWidth + mDragWipeOffset) {

                    if (Math.abs(xDelta) > Math.abs(yDelta) && Math.abs(xDelta) > 20) { //X滑动主导
                        intercept = true;
                    } else {
                        intercept = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        xLast = x;
        yLast = y;
        xIntercept = x;
        yIntercept = y;
        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float xDelta = x - xLast;
                float offset = xDelta;
                touchMove_deal(offset);
                break;
            case MotionEvent.ACTION_UP:
                touchUp_deal();
                break;
        }
        xLast = x;
        yLast = y;
        return false;
    }
    private void dealScroll() {
        //最小缩放值
        float progress = 1 - getScrollX() * 1.0f / (mScreenWidth - mMenuOffset);
        //移动动画处理
//        switch (mTransInt) {
//            case 1:
//                mMenuView.setTranslationX(getScrollX());
//                break;
//            case 2:
//                break;
//            case 3:
        mMenuView.setTranslationX(getScrollX() * progress);
//                break;
//        }
        //状态栏跟随内容区域滑动
        if (isTranslate) {
            statusView.setTranslationX(progress * (mScreenWidth - mMenuOffset));
        }
        //渐变状态栏
        if (mListener != null) {//进度监听
            mListener.onProgressChange(progress);
        }
    }

    //滑动处理
    private void touchMove_deal(float offset) {
        if (getScrollX() - offset <= 0) {
            offset = 0;
        } else if (getScrollX() + mScreenWidth - offset > mScreenWidth * 2 - mMenuOffset) {
            offset = 0;
        }
        scrollBy((int) (-offset), 0); //跟随拖动
        dealScroll();
    }


    //抬起处理
    private void touchUp_deal() {
        if (getScrollX() < (mScreenWidth - mMenuOffset) / 2) {
            //滑动菜单
            showMenu();
        } else {
            //滑动到内容
            hideMenu();
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            dealScroll();
            postInvalidate();
        }
        super.computeScroll();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isMeasured) {
            mMenuView = getChildAt(0);
            mContentView = getChildAt(1);
            mMenuView.getLayoutParams().width = mScreenWidth - mMenuOffset;
            mMenuView.getLayoutParams().height = mScreenHeight;
            mContentView.getLayoutParams().width = mScreenWidth;
            mContentView.getLayoutParams().height = mScreenHeight;
            measureChild(mMenuView, widthMeasureSpec, heightMeasureSpec);
            measureChild(mContentView, widthMeasureSpec, heightMeasureSpec);
            isMeasured = true;
        }
        setMeasuredDimension(mScreenWidth * 2 - mMenuOffset, mScreenHeight);
    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (b) {
            mContentView.setClickable(true);
            mMenuView.setClickable(true);
            mMenuView.setBackgroundColor(Color.TRANSPARENT);
            mMenuView.layout(0, 0, mScreenWidth - mMenuOffset, mScreenHeight);
            mContentView.layout(mScreenWidth - mMenuOffset, 0, mScreenWidth - mMenuOffset + mScreenWidth, mScreenHeight);
            scrollTo(mScreenWidth - mMenuOffset, 0);
        }
    }

}
