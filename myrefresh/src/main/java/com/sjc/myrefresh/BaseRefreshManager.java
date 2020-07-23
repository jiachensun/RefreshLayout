package com.sjc.myrefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @Author sjc
 * @Date 2020/7/23
 * Description：模板模式
 */
public abstract class BaseRefreshManager {

    private LayoutInflater layoutInflater;


    public BaseRefreshManager(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    /**
     * 设置监听器
     * @param listener 监听器
     */
    protected abstract void setRefreshListener(IRefreshListener listener);

    /**
     * 刷新头
     * @return View
     */
    protected abstract View getHeaderView();

    /**
     * 正在下拉刷新
     * @param persent 下拉进度
     */
    protected abstract void onDownRefresh(int persent);

    /**
     * 正在刷新 手指抬起
     */
    protected abstract void onRefreshing();

}
