package com.ellfors.testdemo.util;


import com.ellfors.testdemo.base.recyclerview.BaseRecyclerData;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换请求数据工具类
 * 2018/4/21 19:58
 */
public class ListUtil
{
    /**
     * 将T列表转换为BaseRecyclerData列表
     *
     * @param response 原列表数据
     * @param type     类型
     * @return BaseRecyclerData列表
     */
    public static <T> List<BaseRecyclerData> getData(List<T> response, int type)
    {
        List<BaseRecyclerData> datas = new ArrayList<>();
        if (response != null && response.size() > 0)
        {
            for (T t : response)
            {
                datas.add(new BaseRecyclerData(t, type));
            }
        }
        return datas;
    }

    /**
     * 将T列表转换为BaseRecyclerData列表
     *
     * @param response 原列表数据
     * @return BaseRecyclerData列表
     */
    public static <T> List<BaseRecyclerData> getData(List<T> response)
    {
        List<BaseRecyclerData> datas = new ArrayList<>();
        if (response != null && response.size() > 0)
        {
            for (T t : response)
            {
                datas.add(new BaseRecyclerData(t));
            }
        }
        return datas;
    }
}
