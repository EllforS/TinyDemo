package com.ellfors.testdemo.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ellfors.testdemo.R;

import java.util.List;

/**
 * DialogUtil
 * 2019/3/28 10:25
 */
public class DialogUtil
{
    /**
     * 滑出选择Dialog
     *
     * @param mContext 上下文
     * @param contents Item列表
     * @param listener 监听
     */
    public static void showChooseSelectDialog(Activity mContext, List<String> contents, final DialogChooseListAdapter.OnItemClickListener listener)
    {
        LayoutInflater layoutInflater = mContext.getLayoutInflater();
        View customLayout = layoutInflater.inflate(
                R.layout.common_dialog_select,
                (ViewGroup) mContext.findViewById(R.id.rel_dialog_contact_change_headimg));
        TextView btnCancel = customLayout.findViewById(R.id.dialog_merchantspro_dismiss);
        RecyclerView recyclerView = customLayout.findViewById(R.id.rv_dialog_common_choose);
        DialogChooseListAdapter adapter = new DialogChooseListAdapter(mContext, contents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.choose_select_dialog_style).setView(customLayout).show();
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.choose_select_dialog_anim);
        adapter.setOnItemClickListener(new DialogChooseListAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                alertDialog.dismiss();
                listener.onItemClick(view, position);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                alertDialog.dismiss();
            }
        });
    }
}
