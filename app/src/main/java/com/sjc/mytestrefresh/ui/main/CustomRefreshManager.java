package com.sjc.mytestrefresh.ui.main;

import android.content.Context;
import android.view.View;

import com.sjc.myrefresh.BaseRefreshManager;
import com.sjc.myrefresh.IRefreshListener;

/**
 * @Author sjc
 * @Date 2020/7/23
 * Description：
 */
public class CustomRefreshManager extends BaseRefreshManager {

    public CustomRefreshManager(Context context) {
        super(context);
    }

    /**
     * 设置监听器
     *
     * @param listener 监听器
     */
    @Override
    protected void setRefreshListener(IRefreshListener listener) {

    }

    /**
     * 刷新头
     *
     * @return View
     */
    @Override
    protected View getHeaderView() {
        return null;
    }

    /**
     * 下拉刷新
     */
    @Override
    protected void onDownRefresh(int persent) {

    }

    /**
     * 正在刷新 手指抬起
     */
    @Override
    protected void onRefreshing() {

    }
}
