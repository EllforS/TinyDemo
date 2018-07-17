package com.ellfors.testdemo.base.recyclerview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * RecyclerView类型权重占比对象
 */
public class BaseRecyclerTypeSpanCount implements Parcelable
{
    private Integer type;       //类型
    private Integer spanCount;  //权重

    public BaseRecyclerTypeSpanCount()
    {

    }

    public BaseRecyclerTypeSpanCount(Integer type, Integer spanCount)
    {
        this.type = type;
        this.spanCount = spanCount;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getSpanCount()
    {
        return spanCount;
    }

    public void setSpanCount(Integer spanCount)
    {
        this.spanCount = spanCount;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(this.type);
        dest.writeValue(this.spanCount);
    }

    protected BaseRecyclerTypeSpanCount(Parcel in)
    {
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.spanCount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<BaseRecyclerTypeSpanCount> CREATOR = new Creator<BaseRecyclerTypeSpanCount>()
    {
        @Override
        public BaseRecyclerTypeSpanCount createFromParcel(Parcel source)
        {
            return new BaseRecyclerTypeSpanCount(source);
        }

        @Override
        public BaseRecyclerTypeSpanCount[] newArray(int size)
        {
            return new BaseRecyclerTypeSpanCount[size];
        }
    };
}