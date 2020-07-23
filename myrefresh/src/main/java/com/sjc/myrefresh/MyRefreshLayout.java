package com.sjc.myrefresh;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @Author sjc
 * @Date 2020/7/23
 * Description：控件化 下拉刷新
 */
public class MyRefreshLayout extends LinearLayout {
    private BaseRefreshManager refreshManager;

    private View refreshHeaderView;
    private Context mContext;
    private float downPointY;
    private int defaultRefreshHeaderViewHeight;
    private int minDownHeight;

    public MyRefreshLayout(Context context) {
        super(context);
        initView(context);
    }

    public MyRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
    }

    public void setRrfreshManager(BaseRefreshManager refreshManager) {
        this.refreshManager = refreshManager;
        initHeaderView();
    }

    public DefaultRefreshManager getDefaultRefreshManager() {
        DefaultRefreshManager defaultRefreshManager = new DefaultRefreshManager(mContext);
        this.refreshManager = defaultRefreshManager;
        initHeaderView();
        return defaultRefreshManager;
    }

    private void initHeaderView() {
        // 设置垂直布局
        setOrientation(VERTICAL);
        // 开始隐藏刷新提示
        refreshHeaderView = refreshManager.getHeaderView();
        // 构造方法中拿不到view的宽高，需要先度量一下，然后获取度量的宽高
        refreshHeaderView.measure(0,0);
        int refreshHeaderViewMeasuredHeight = refreshHeaderView.getMeasuredHeight();
        minDownHeight = (int) (refreshHeaderViewMeasuredHeight * 0.7);
        defaultRefreshHeaderViewHeight = - refreshHeaderViewMeasuredHeight;
        // 设置headerview的初始布局
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, refreshHeaderViewMeasuredHeight);
        layoutParams.topMargin = defaultRefreshHeaderViewHeight;
        addView(refreshHeaderView, 0, layoutParams);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 按下
            downPointY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // 移动
            float moveDistance = event.getY() - downPointY;
            if (moveDistance > 0) {
                // 向下滑动
                int topMargin = (int) Math.min(0, defaultRefreshHeaderViewHeight + moveDistance/1.8f);
                LayoutParams layoutParams = (LayoutParams) refreshHeaderView.getLayoutParams();
                layoutParams.topMargin = topMargin;
                refreshHeaderView.setLayoutParams(layoutParams);

                if (topMargin < - minDownHeight) {
                    // 正在下拉 松开不能刷新
                    int persent = (int) (((defaultRefreshHeaderViewHeight - topMargin) / (float)(defaultRefreshHeaderViewHeight)) * 100);
                    refreshManager.onDownRefresh(persent);
                } else if (topMargin > - minDownHeight && topMargin < 0) {
                    // 正在下拉 松开可刷新
                    int persent = (int) (((defaultRefreshHeaderViewHeight - topMargin) / (float)(defaultRefreshHeaderViewHeight)) * 100);
                    refreshManager.onDownRefresh(persent);
                } else if (topMargin == 0) {
                    refreshManager.onDownRefresh(100);
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // 抬起
            float moveDistance = event.getY() - downPointY;
            if (moveDistance/1.8 >= minDownHeight) {
                // 执行刷新 正在刷新
                LayoutParams layoutParams = (LayoutParams) refreshHeaderView.getLayoutParams();
                layoutParams.topMargin = 0;
                refreshHeaderView.setLayoutParams(layoutParams);
                refreshManager.onRefreshing();
            } else {
                // 回弹 无动作
                LayoutParams layoutParams = (LayoutParams) refreshHeaderView.getLayoutParams();
                layoutParams.topMargin = defaultRefreshHeaderViewHeight;
                refreshHeaderView.setLayoutParams(layoutParams);
            }
        }
        return true;
    }

    /**
     * 设置刷新状态监听
     */
    public void setRefreshListener(IRefreshListener refreshListener) {
        refreshManager.setRefreshListener(refreshListener);
    }

    /**
     * 还原refreshheader
     */
    public void restoreRefreshHeader() {
        // 刷新完成 回弹
        LayoutParams layoutParams = (LayoutParams) refreshHeaderView.getLayoutParams();
        layoutParams.topMargin = defaultRefreshHeaderViewHeight;
        refreshHeaderView.setLayoutParams(layoutParams);
    }
}
