package com.ellfors.testdemo.biz.refresh

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerAdapter
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerData
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerViewHolder

/**
 * RefreshAdapter
 * 2018/9/3 14:21
 */
class RefreshAdapter constructor(mContext: Context?, items: MutableList<BaseRecyclerData>?) : BaseRecyclerAdapter(mContext, items)
{
    override fun onCreate(parent: ViewGroup?, viewType: Int): BaseRecyclerViewHolder
    {
        val itemHolder = ItemViewHolder(getItemView(R.layout.item_refresh, parent), this)
        itemHolder.setOnClickListener(itemHolder.tv_item)
        return itemHolder
    }

    override fun onBind(holder: BaseRecyclerViewHolder?, position: Int)
    {
        val text = items[position].data as String
        val itemHolder = holder as ItemViewHolder
        itemHolder.tv_item.text = text
    }

    class ItemViewHolder(itemView: View, adapter: BaseRecyclerAdapter) : BaseRecyclerViewHolder(itemView, adapter)
    {
        @BindView(R.id.tv_refresh)
        lateinit var tv_item: TextView
    }
}