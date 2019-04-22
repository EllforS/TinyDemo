package com.ellfors.testdemo.biz.touchlist

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
 * TouchListAdapter
 * 2019/4/22 12:20
 */
class TouchListAdapter(mContext: Context, items: List<BaseRecyclerData>) : BaseRecyclerAdapter(mContext, items)
{
    override fun onCreate(parent: ViewGroup?, viewType: Int): BaseRecyclerViewHolder
    {
        return ItemViewHolder(getItemView(R.layout.item_touch_list, parent), this)
    }

    override fun onBind(holder: BaseRecyclerViewHolder?, position: Int)
    {
        val itemHolder = holder as ItemViewHolder
        val str = items[position].data as String
        itemHolder.mText.text = str
    }

    class ItemViewHolder(itemView: View, adapter: BaseRecyclerAdapter) : BaseRecyclerViewHolder(itemView, adapter)
    {
        @BindView(R.id.tv_touch_list)
        lateinit var mText: TextView
    }
}