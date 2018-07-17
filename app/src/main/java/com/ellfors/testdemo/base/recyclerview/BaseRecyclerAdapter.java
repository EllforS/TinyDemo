package com.ellfors.testdemo.base.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

/**
 * RecyclerView适配器基类
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder>
{
    /**
     * ********************* 抽象方法与接口 *************************************
     */
    protected void init()
    {
    }

    protected abstract BaseRecyclerViewHolder onCreate(ViewGroup parent, int viewType);

    protected abstract void onBind(BaseRecyclerViewHolder holder, int position);

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position, BaseRecyclerData bean);
    }

    public interface OnItemLongClickListener
    {
        void onItemLongClick(View view, int position, BaseRecyclerData bean);
    }

    public interface OnTextWatcher
    {
        void afterTextChanged(EditText etView, int position, String s);
    }

    /**
     * ************************ 初始化设置 ****************************************
     */
    protected Context mContext;
    protected List<BaseRecyclerData> items;
    protected LayoutInflater mInflater;
    protected OnItemClickListener mListener;
    protected OnItemLongClickListener mLongListener;
    protected OnTextWatcher textChange;
    protected List<BaseRecyclerTypeSpanCount> mSpanList;
    //默认权重
    private int defWeight = 1;

    public BaseRecyclerAdapter(Context mContext, List<BaseRecyclerData> items)
    {
        this.mContext = mContext;
        this.items = items;
        mInflater = LayoutInflater.from(mContext);
        init();
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position)
    {
        onBind(holder, position);
    }

    @Override
    public int getItemCount()
    {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (null != items && items.size() > position)
        {
            BaseRecyclerData obj = items.get(position);
            if (null != obj)
                return obj.getType();
        }
        return 0;
    }

    /* GridLayoutManager 权重设置 */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager)
        {
            GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    if (mSpanList != null && items.size() > 0)
                    {
                        for (BaseRecyclerTypeSpanCount bean : mSpanList)
                        {
                            if (getItemViewType(position) == bean.getType())
                                return bean.getSpanCount();
                        }
                    }
                    return defWeight;
                }
            });
        }
    }

    /**
     * ******************************** 外部暴露的方法 **************************************
     */
    /* 获取ItemLayout的View实例 */
    protected View getItemView(int layoutId, ViewGroup parent)
    {
        return mInflater.inflate(layoutId, parent, false);
    }

    /* 设置默认grid的权重 */
    public void setDefGridWeight(int defWeight)
    {
        this.defWeight = defWeight;
    }

    /* 获取指定指针的数据 */
    public BaseRecyclerData getData(int position)
    {
        if (null == items)
        {
            return null;
        }
        return items.get(position);
    }

    /* 初始化数据 */
    public void setData(List<BaseRecyclerData> msg)
    {
        this.items = msg;
        notifyDataSetChanged();
    }

    /* 添加数据 */
    public void addData(List<BaseRecyclerData> msg)
    {
        if (msg != null && msg.size() > 0)
        {
            items.addAll(msg);
            notifyDataSetChanged();
        }
    }

    /* 设置权重占比数据 */
    public void setSpanCountList(BaseRecyclerTypeSpanCount[] list)
    {
        if (list != null && list.length > 0)
            this.mSpanList = Arrays.asList(list);
    }

    /* 设置权重占比数据 */
    public void setSpanCountList(List<BaseRecyclerTypeSpanCount> list)
    {
        if (list != null && list.size() > 0)
            this.mSpanList = list;
    }

    /* 设置Item点击监听 */
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        if (listener != null)
            this.mListener = listener;
    }

    /* 设置Item长按监听 */
    public void setOnItemLongClickListener(OnItemLongClickListener listener)
    {
        if (listener != null)
            this.mLongListener = listener;
    }

    /* 根据编辑状态设置回调 */
    protected class BaseRecyclerTextWatcher implements TextWatcher
    {
        private EditText etView;
        private BaseRecyclerViewHolder viewHolder;
        private int position;

        public BaseRecyclerTextWatcher(EditText etView, BaseRecyclerViewHolder viewHolder)
        {
            this.etView = etView;
            this.viewHolder = viewHolder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            position = viewHolder.getAdapterPosition();
            if (position == RecyclerView.NO_POSITION)
                position = viewHolder.getPosition();
            if (position != RecyclerView.NO_POSITION)
                textChange.afterTextChanged(etView, position, s.toString().trim());
        }
    }

}
