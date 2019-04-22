package com.ellfors.testdemo.biz.touchlist;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE;
import static android.support.v7.widget.helper.ItemTouchHelper.DOWN;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;
import static android.support.v7.widget.helper.ItemTouchHelper.UP;

/**
 * ItemDrag
 * 2019/4/22 12:23
 */
public class MyItemTouchHelper extends ItemTouchHelper.Callback
{
    /**
     * 拖拽时的颜色
     */
    private static final int IDLE_COLOR = 0xff999999;
    /**
     * 空闲时的颜色
     */
    private static final int DEFAULT_COLOR = 0xffffffff;

    private ItemTouchMoveListener mItemTouchMoveListener;
    private int mFromPos = -1;
    private int mTargetPos = -1;

    public MyItemTouchHelper(ItemTouchMoveListener itemTouchMoveListener)
    {
        mItemTouchMoveListener = itemTouchMoveListener;
    }

    /**
     * callback回调监听先调用的，用来判断是什么动作，比如判断方向（意思就是我要监听那个方向的拖到）
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        int dragFlags = 0;
        int swipeFlags = 0;
        //监听侧滑删除
//        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
            dragFlags = UP | DOWN | LEFT | RIGHT;
        else if (layoutManager instanceof LinearLayoutManager)
            dragFlags = UP | DOWN;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 当移动的时候回调的方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        //条目布局不同，就不交换位置
        if (viewHolder.getItemViewType() != target.getItemViewType())
            return false;
        //adapter.notifyItemMoved(fromPosition，toPosition);改变拖拽item位置
        mItemTouchMoveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    /**
     * 侧滑
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        //监听侧滑1:删除数据；2：adapter.notifyItemRemove（position）
//        mItemTouchMoveListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    /**
     * item改变状态
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState)
    {
        if (actionState != ACTION_STATE_IDLE)
        {
            //判断选择状态,改变背景颜色
            viewHolder.itemView.setBackgroundColor(IDLE_COLOR);
            //记录按下的Position
            mFromPos = viewHolder.getAdapterPosition();
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 状态背景颜色复原
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        //重置颜色
        viewHolder.itemView.setBackgroundColor(DEFAULT_COLOR);
        //记录抬起时的Position
        mTargetPos = viewHolder.getAdapterPosition();
        if (mFromPos != -1 && mTargetPos != -1 && mFromPos != mTargetPos)
            mItemTouchMoveListener.onItemMoveComplete(mFromPos, mTargetPos);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
//        //dx,侧滑改变特效
//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
//        {
//            //设置透明度1-0
//            float result = Math.abs(dX) / viewHolder.itemView.getWidth();
//            viewHolder.itemView.setAlpha(result);
//            //旋转
//            viewHolder.itemView.setScaleX(result);
//            viewHolder.itemView.setScaleY(result);
//        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    /**
     * 是否允许长按拖拽
     */
    @Override
    public boolean isLongPressDragEnabled()
    {
        return true;
    }

    public interface ItemTouchMoveListener
    {
        /**
         * 当拖拽的时候回调
         * 可以在此方法实现拖拽条目，并实现刷新效果
         *
         * @param fromPosition 从什么位置拖拽
         * @param toPosition   到什么位置
         */
        void onItemMove(int fromPosition, int toPosition);

        /**
         * 当拖拽完成时回调
         *
         * @param fromPosition 从什么位置拖拽
         * @param toPosition   到什么位置
         */
        void onItemMoveComplete(int fromPosition, int toPosition);

        /**
         * 当条目被移除的回调
         *
         * @param position 那一条被移除
         */
//        void onItemRemove(int position);
    }
}