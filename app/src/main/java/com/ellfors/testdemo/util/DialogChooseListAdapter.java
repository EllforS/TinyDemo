package com.ellfors.testdemo.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ellfors.testdemo.R;

import java.util.List;

/**
 * 滑出弹窗适配器
 */
public class DialogChooseListAdapter extends RecyclerView.Adapter<DialogChooseListAdapter.ItemViewHolder>
{
    private Context context;
    private List<String> list;

    public DialogChooseListAdapter(Context context, List<String> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_dialog_common_choose, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position)
    {
        if (list.size() == 1)
        {
            holder.btnChoose.setBackgroundResource(R.drawable.shape_dialgo_common_choose);
            holder.vLine.setVisibility(View.INVISIBLE);
        }
        else if (position == 0)
        {
            holder.btnChoose.setBackgroundResource(R.drawable.shape_dialgo_common_choose_top);
            holder.vLine.setVisibility(View.VISIBLE);
        }
        else if (position == list.size() - 1)
        {
            holder.btnChoose.setBackgroundResource(R.drawable.shape_dialgo_common_choose_bottom);
            holder.vLine.setVisibility(View.GONE);
        }
        else
        {
            holder.btnChoose.setBackgroundResource(R.color.white);
            holder.vLine.setVisibility(View.VISIBLE);
        }
        holder.btnChoose.setText(list.get(position));
        holder.btnChoose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list == null || list.size() == 0 ? 0 : list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView btnChoose;
        View vLine;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            btnChoose = (TextView) itemView.findViewById(R.id.btn_dialog_common_choose);
            vLine = itemView.findViewById(R.id.v_dialog_common_choose);
        }
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

}