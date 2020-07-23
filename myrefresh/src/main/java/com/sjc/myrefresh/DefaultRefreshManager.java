package com.sjc.myrefresh;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * @Author sjc
 * @Date 2020/7/23
 * Description：默认manager
 */
public class DefaultRefreshManager extends BaseRefreshManager {

    private TextView refreshInfoTextView;
    private IRefreshListener refreshListener;

    public DefaultRefreshManager(Context context) {
        super(context);
    }

    /**
     * 设置监听器
     *
     * @param listener 监听器
     */
    @Override
    protected void setRefreshListener(IRefreshListener listener) {
        this.refreshListener = listener;
    }

    @Override
    protected View getHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.refresh_layout, null, false);
        refreshInfoTextView = view.findViewById(R.id.refresh_header_view);
        return view;
    }

    @Override
    protected void onDownRefresh(int persent) {
        refreshInfoTextView.setTextColor(Color.GRAY);
        if (persent < 70) {
            refreshInfoTextView.setText("下拉刷新 " + persent + "%");
        } else if (persent < 100) {
            refreshInfoTextView.setText("松开刷新 " + persent + "%");
        } else {
            refreshInfoTextView.setTextColor(Color.YELLOW);
            refreshInfoTextView.setText("松开刷新");
        }
    }

    @Override
    protected void onRefreshing() {
        refreshInfoTextView.setTextColor(Color.BLUE);
        refreshInfoTextView.setText("刷新中");
        refreshListener.onRefreshing();
    }

    /**
     * 刷新成功 回滚到初始状态
     */
    public void onRefreshSucceed() {
        refreshInfoTextView.setTextColor(Color.GREEN);
        refreshInfoTextView.setText("刷新成功");
    }

    /**
     * 刷新失败 回滚到初始状态
     */
    public void onRefreshFailed() {
        refreshInfoTextView.setTextColor(Color.RED);
        refreshInfoTextView.setText("刷新失败");
    }
}
