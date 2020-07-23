package com.sjc.myrefresh;

/**
 * @Author sjc
 * @Date 2020/7/23
 * Description：
 */
public interface IRefreshListener {

    /**
     * 正在刷新 需要通知上层
     */
    void onRefreshing();
}
