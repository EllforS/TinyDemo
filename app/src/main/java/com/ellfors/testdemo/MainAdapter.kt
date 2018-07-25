package com.ellfors.testdemo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerAdapter
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerData
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerViewHolder
import com.ellfors.testdemo.model.MainItemBean

class MainAdapter(mContext: Context, items: MutableList<BaseRecyclerData>) : BaseRecyclerAdapter(mContext, items)
{
    override fun onCreate(parent: ViewGroup?, viewType: Int): BaseRecyclerViewHolder
    {
        val itemHolder = ItemViewHolder(getItemView(R.layout.item_main, parent), this)
        itemHolder.setOnClickListener(itemHolder.tvMain)
        return itemHolder
    }

    override fun onBind(holder: BaseRecyclerViewHolder?, position: Int)
    {
        val bean = items[position].data as MainItemBean
        val itemHolder = holder as ItemViewHolder
        itemHolder.tvMain.text = bean.text
    }

    class ItemViewHolder(itemView: View, adapter: BaseRecyclerAdapter) : BaseRecyclerViewHolder(itemView, adapter)
    {
        @BindView(R.id.item_tv_main)
        lateinit var tvMain: TextView
    }
}