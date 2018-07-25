package com.ellfors.testdemo.biz.right_grid

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerAdapter
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerData
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerViewHolder

class RightGridAdapter constructor(mContext: Context?, items: MutableList<BaseRecyclerData>?) : BaseRecyclerAdapter(mContext, items)
{
    override fun onCreate(parent: ViewGroup?, viewType: Int): BaseRecyclerViewHolder
    {
        return ItemViewHolder(getItemView(R.layout.item_right_grid, parent), this)
    }

    override fun onBind(holder: BaseRecyclerViewHolder?, position: Int)
    {
        val str = items[position].data as String
        val itemHolder = holder as ItemViewHolder
        itemHolder.tv_text.text = str
    }

    class ItemViewHolder constructor(itemView: View?, adapter: BaseRecyclerAdapter?) : BaseRecyclerViewHolder(itemView, adapter)
    {
        @BindView(R.id.tv_text)
        lateinit var tv_text: TextView
    }

}