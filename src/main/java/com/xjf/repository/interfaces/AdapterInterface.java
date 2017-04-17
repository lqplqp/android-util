package com.xjf.repository.interfaces;

import com.xjf.repository.bean.Domain;

import java.util.List;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/11/9--9:36
 * Function: 适配器接口
 * ModifyHistory:
 * -----------------------------------------------------------------
 */

public interface AdapterInterface<T extends Domain> {

    /**
     * 更新UI
     */
    void updateUI();


    /**
     * 首次添加数据
     *
     * @param dataListTemp
     */
    void addDataAndUpdateUI(final List<T> dataListTemp);


    /**
     * 添加单个对象并刷新
     *
     * @param data
     */
    void addDataAndUpdateUI(final T data);


    /**
     * 添加单个数据对象并刷新 回调
     *
     * @param data
     * @param callBack
     */
    void addDataAndUpdateUI(final T data, final CallBack callBack);

    /**
     * 设置数据
     *
     * @param dataListTemp
     */
    void setDataAndUpdateUI(final List<T> dataListTemp);

    /**
     * 带回调 的设置数据
     *
     * @param dataListTemp
     */
    void setDataAndUpdateUI(final List<T> dataListTemp, final CallBack callback);

    /**
     * 先清除再添加更新 无回调
     *
     * @param dataListTemp
     */
    void clearDataAndAddUpdateUI(final List<T> dataListTemp);

    /**
     * 先清除再添加更新数据  后回调
     *
     * @param dataListTemp
     * @param callBack
     */
    void clearDataAndAddUpdateUI(final List<T> dataListTemp, final CallBack callBack);


    /**
     * 删除指定的position
     *
     * @param position
     */
    void deletePositionAndUpdateUI(int position);


    /**
     * 删除指定的position
     *
     * @param position 指定的索引
     * @param callBack 回调
     */
    void deletePositionAndUpdateUI(int position, CallBack callBack);

    /**
     * 清空数据
     */
    void clearDataAndUpdateUI();


    /***
     * 清除后添加最新的数据
     *
     * @param dataListTemp
     * @param topIndex
     */
    void clearAddLatestDataAndUpdateUI(final List<T> dataListTemp, final int topIndex);

    /***
     * 清除后添加最新的数据
     *
     * @param dataListTemp
     * @param callBack
     * @param topIndex
     */
    void clearAddLatestDataAndUpdateUI(final List<T> dataListTemp, final int topIndex, final CallBack callBack);


}
