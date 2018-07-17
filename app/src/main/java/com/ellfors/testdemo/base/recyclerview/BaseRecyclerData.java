package com.ellfors.testdemo.base.recyclerview;

/**
 * BaseRecyclerData
 * 2018/3/26 15:02
 */
public class BaseRecyclerData
{
    private Object data;
    private int type;

    public BaseRecyclerData()
    {

    }

    public BaseRecyclerData(Object data)
    {
        this.data = data;
        this.type = 0;
    }

    public BaseRecyclerData(Object data, int type)
    {
        this.data = data;
        this.type = type;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
}
