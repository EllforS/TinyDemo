package com.ellfors.testdemo.permission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public abstract class PermissionDialog
{
    public PermissionDialog()
    {
    }

    public static PermissionDialog.Builder newBuilder(Context context)
    {
        return (PermissionDialog.Builder) (VERSION.SDK_INT >= 21 ? new PermissionDialog.APi21Builder(context) : new PermissionDialog.Api20Builder(context));
    }

    public static PermissionDialog.Builder newBuilder(Context context, int themeResId)
    {
        return (PermissionDialog.Builder) (VERSION.SDK_INT >= 21 ? new PermissionDialog.APi21Builder(context, themeResId) : new PermissionDialog.Api20Builder(context, themeResId));
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static PermissionDialog.Builder build(Context context)
    {
        return newBuilder(context);
    }

    /**
     * @deprecated
     */
    public static PermissionDialog.Builder build(Context context, int themeResId)
    {
        return newBuilder(context, themeResId);
    }

    public abstract void show();

    public abstract void dismiss();

    public abstract boolean isShowing();

    public abstract void cancel();

    public abstract Button getButton(int var1);

    @Nullable
    public abstract ListView getListView();

    @NonNull
    public abstract Context getContext();

    @Nullable
    public abstract View getCurrentFocus();

    @NonNull
    public abstract LayoutInflater getLayoutInflater();

    @Nullable
    public abstract Activity getOwnerActivity();

    public abstract int getVolumeControlStream();

    @Nullable
    public abstract Window getWindow();

    private static class Api20Builder implements PermissionDialog.Builder
    {
        private android.support.v7.app.AlertDialog.Builder builder;

        private Api20Builder(@NonNull Context context)
        {
            this(context, 0);
        }

        private Api20Builder(@NonNull Context context, @StyleRes int themeResId)
        {
            this.builder = new android.support.v7.app.AlertDialog.Builder(context, themeResId);
        }

        @NonNull
        public Context getContext()
        {
            return this.builder.getContext();
        }

        public PermissionDialog.Builder setTitle(@StringRes int titleId)
        {
            this.builder.setTitle(titleId);
            return this;
        }

        public PermissionDialog.Builder setTitle(CharSequence title)
        {
            this.builder.setTitle(title);
            return this;
        }

        public PermissionDialog.Builder setCustomTitle(View customTitleView)
        {
            this.builder.setCustomTitle(customTitleView);
            return this;
        }

        public PermissionDialog.Builder setMessage(@StringRes int messageId)
        {
            this.builder.setMessage(messageId);
            return this;
        }

        public PermissionDialog.Builder setMessage(CharSequence message)
        {
            this.builder.setMessage(message);
            return this;
        }

        public PermissionDialog.Builder setIcon(@DrawableRes int iconId)
        {
            this.builder.setIcon(iconId);
            return this;
        }

        public PermissionDialog.Builder setIcon(Drawable icon)
        {
            this.builder.setIcon(icon);
            return this;
        }

        public PermissionDialog.Builder setIconAttribute(@AttrRes int attrId)
        {
            this.builder.setIconAttribute(attrId);
            return this;
        }

        public PermissionDialog.Builder setPositiveButton(@StringRes int textId, OnClickListener listener)
        {
            this.builder.setPositiveButton(textId, listener);
            return this;
        }

        public PermissionDialog.Builder setPositiveButton(CharSequence text, OnClickListener listener)
        {
            this.builder.setPositiveButton(text, listener);
            return this;
        }

        public PermissionDialog.Builder setNegativeButton(@StringRes int textId, OnClickListener listener)
        {
            this.builder.setNegativeButton(textId, listener);
            return this;
        }

        public PermissionDialog.Builder setNegativeButton(CharSequence text, OnClickListener listener)
        {
            this.builder.setNegativeButton(text, listener);
            return this;
        }

        public PermissionDialog.Builder setNeutralButton(@StringRes int textId, OnClickListener listener)
        {
            this.builder.setNeutralButton(textId, listener);
            return this;
        }

        public PermissionDialog.Builder setNeutralButton(CharSequence text, OnClickListener listener)
        {
            this.builder.setNeutralButton(text, listener);
            return this;
        }

        public PermissionDialog.Builder setCancelable(boolean cancelable)
        {
            this.builder.setCancelable(cancelable);
            return this;
        }

        public PermissionDialog.Builder setOnCancelListener(OnCancelListener onCancelListener)
        {
            this.builder.setOnCancelListener(onCancelListener);
            return this;
        }

        public PermissionDialog.Builder setOnDismissListener(OnDismissListener onDismissListener)
        {
            this.builder.setOnDismissListener(onDismissListener);
            return this;
        }

        public PermissionDialog.Builder setOnKeyListener(OnKeyListener onKeyListener)
        {
            this.builder.setOnKeyListener(onKeyListener);
            return this;
        }

        public PermissionDialog.Builder setItems(@ArrayRes int itemsId, OnClickListener listener)
        {
            this.builder.setItems(itemsId, listener);
            return this;
        }

        public PermissionDialog.Builder setItems(CharSequence[] items, OnClickListener listener)
        {
            this.builder.setItems(items, listener);
            return this;
        }

        public PermissionDialog.Builder setAdapter(ListAdapter adapter, OnClickListener listener)
        {
            this.builder.setAdapter(adapter, listener);
            return this;
        }

        public PermissionDialog.Builder setCursor(Cursor cursor, OnClickListener listener, String labelColumn)
        {
            this.builder.setCursor(cursor, listener, labelColumn);
            return this;
        }

        public PermissionDialog.Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems, OnMultiChoiceClickListener listener)
        {
            this.builder.setMultiChoiceItems(itemsId, checkedItems, listener);
            return this;
        }

        public PermissionDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, OnMultiChoiceClickListener listener)
        {
            this.builder.setMultiChoiceItems(items, checkedItems, listener);
            return this;
        }

        public PermissionDialog.Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, OnMultiChoiceClickListener listener)
        {
            this.builder.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
            return this;
        }

        public PermissionDialog.Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, OnClickListener listener)
        {
            this.builder.setSingleChoiceItems(itemsId, checkedItem, listener);
            return this;
        }

        public PermissionDialog.Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, OnClickListener listener)
        {
            this.builder.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
            return this;
        }

        public PermissionDialog.Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, OnClickListener listener)
        {
            this.builder.setSingleChoiceItems(items, checkedItem, listener);
            return this;
        }

        public PermissionDialog.Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, OnClickListener listener)
        {
            this.builder.setSingleChoiceItems(adapter, checkedItem, listener);
            return this;
        }

        public PermissionDialog.Builder setOnItemSelectedListener(OnItemSelectedListener listener)
        {
            this.builder.setOnItemSelectedListener(listener);
            return this;
        }

        public PermissionDialog.Builder setView(int layoutResId)
        {
            this.builder.setView(layoutResId);
            return this;
        }

        public PermissionDialog.Builder setView(View view)
        {
            this.builder.setView(view);
            return this;
        }

        public PermissionDialog create()
        {
            return new PermissionDialog.Api20Dialog(this.builder.create());
        }

        public PermissionDialog show()
        {
            PermissionDialog dialog = this.create();
            dialog.show();
            return dialog;
        }
    }

    private static class APi21Builder implements PermissionDialog.Builder
    {
        private android.app.AlertDialog.Builder builder;

        private APi21Builder(@NonNull Context context)
        {
            this(context, 0);
        }

        private APi21Builder(@NonNull Context context, @StyleRes int themeResId)
        {
            this.builder = new android.app.AlertDialog.Builder(context, themeResId);
        }

        @NonNull
        public Context getContext()
        {
            return this.builder.getContext();
        }

        public PermissionDialog.Builder setTitle(@StringRes int titleId)
        {
            this.builder.setTitle(titleId);
            return this;
        }

        public PermissionDialog.Builder setTitle(CharSequence title)
        {
            this.builder.setTitle(title);
            return this;
        }

        public PermissionDialog.Builder setCustomTitle(View customTitleView)
        {
            this.builder.setCustomTitle(customTitleView);
            return this;
        }

        public PermissionDialog.Builder setMessage(@StringRes int messageId)
        {
            this.builder.setMessage(messageId);
            return this;
        }

        public PermissionDialog.Builder setMessage(CharSequence message)
        {
            this.builder.setMessage(message);
            return this;
        }

        public PermissionDialog.Builder setIcon(@DrawableRes int iconId)
        {
            this.builder.setIcon(iconId);
            return this;
        }

        public PermissionDialog.Builder setIcon(Drawable icon)
        {
            this.builder.setIcon(icon);
            return this;
        }

        public PermissionDialog.Builder setIconAttribute(@AttrRes int attrId)
        {
            this.builder.setIconAttribute(attrId);
            return this;
        }

        public PermissionDialog.Builder setPositiveButton(@StringRes int textId, OnClickListener listener)
        {
            this.builder.setPositiveButton(textId, listener);
            return this;
        }

        public PermissionDialog.Builder setPositiveButton(CharSequence text, OnClickListener listener)
        {
            this.builder.setPositiveButton(text, listener);
            return this;
        }

        public PermissionDialog.Builder setNegativeButton(@StringRes int textId, OnClickListener listener)
        {
            this.builder.setNegativeButton(textId, listener);
            return this;
        }

        public PermissionDialog.Builder setNegativeButton(CharSequence text, OnClickListener listener)
        {
            this.builder.setNegativeButton(text, listener);
            return this;
        }

        public PermissionDialog.Builder setNeutralButton(@StringRes int textId, OnClickListener listener)
        {
            this.builder.setNeutralButton(textId, listener);
            return this;
        }

        public PermissionDialog.Builder setNeutralButton(CharSequence text, OnClickListener listener)
        {
            this.builder.setNeutralButton(text, listener);
            return this;
        }

        public PermissionDialog.Builder setCancelable(boolean cancelable)
        {
            this.builder.setCancelable(cancelable);
            return this;
        }

        public PermissionDialog.Builder setOnCancelListener(OnCancelListener onCancelListener)
        {
            this.builder.setOnCancelListener(onCancelListener);
            return this;
        }

        public PermissionDialog.Builder setOnDismissListener(OnDismissListener onDismissListener)
        {
            if (VERSION.SDK_INT >= 17)
            {
                this.builder.setOnDismissListener(onDismissListener);
            }

            return this;
        }

        public PermissionDialog.Builder setOnKeyListener(OnKeyListener onKeyListener)
        {
            this.builder.setOnKeyListener(onKeyListener);
            return this;
        }

        public PermissionDialog.Builder setItems(@ArrayRes int itemsId, OnClickListener listener)
        {
            this.builder.setItems(itemsId, listener);
            return this;
        }

        public PermissionDialog.Builder setItems(CharSequence[] items, OnClickListener listener)
        {
            this.builder.setItems(items, listener);
            return this;
        }

        public PermissionDialog.Builder setAdapter(ListAdapter adapter, OnClickListener listener)
        {
            this.builder.setAdapter(adapter, listener);
            return this;
        }

        public PermissionDialog.Builder setCursor(Cursor cursor, OnClickListener listener, String labelColumn)
        {
            this.builder.setCursor(cursor, listener, labelColumn);
            return this;
        }

        public PermissionDialog.Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems, OnMultiChoiceClickListener listener)
        {
            this.builder.setMultiChoiceItems(itemsId, checkedItems, listener);
            return this;
        }

        public PermissionDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, OnMultiChoiceClickListener listener)
        {
            this.builder.setMultiChoiceItems(items, checkedItems, listener);
            return this;
        }

        public PermissionDialog.Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, OnMultiChoiceClickListener listener)
        {
            this.builder.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
            return this;
        }

        public PermissionDialog.Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, OnClickListener listener)
        {
            this.builder.setSingleChoiceItems(itemsId, checkedItem, listener);
            return this;
        }

        public PermissionDialog.Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, OnClickListener listener)
        {
            this.builder.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
            return this;
        }

        public PermissionDialog.Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, OnClickListener listener)
        {
            this.builder.setSingleChoiceItems(items, checkedItem, listener);
            return this;
        }

        public PermissionDialog.Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, OnClickListener listener)
        {
            this.builder.setSingleChoiceItems(adapter, checkedItem, listener);
            return this;
        }

        public PermissionDialog.Builder setOnItemSelectedListener(OnItemSelectedListener listener)
        {
            this.builder.setOnItemSelectedListener(listener);
            return this;
        }

        public PermissionDialog.Builder setView(int layoutResId)
        {
            if (VERSION.SDK_INT >= 21)
            {
                this.builder.setView(layoutResId);
            }

            return this;
        }

        public PermissionDialog.Builder setView(View view)
        {
            this.builder.setView(view);
            return this;
        }

        public PermissionDialog create()
        {
            return new PermissionDialog.Api21Dialog(this.builder.create());
        }

        public PermissionDialog show()
        {
            PermissionDialog dialog = this.create();
            dialog.show();
            return dialog;
        }
    }

    public interface Builder
    {
        @NonNull
        Context getContext();

        PermissionDialog.Builder setTitle(@StringRes int var1);

        PermissionDialog.Builder setTitle(CharSequence var1);

        PermissionDialog.Builder setCustomTitle(View var1);

        PermissionDialog.Builder setMessage(@StringRes int var1);

        PermissionDialog.Builder setMessage(CharSequence var1);

        PermissionDialog.Builder setIcon(@DrawableRes int var1);

        PermissionDialog.Builder setIcon(Drawable var1);

        PermissionDialog.Builder setIconAttribute(@AttrRes int var1);

        PermissionDialog.Builder setPositiveButton(@StringRes int var1, OnClickListener var2);

        PermissionDialog.Builder setPositiveButton(CharSequence var1, OnClickListener var2);

        PermissionDialog.Builder setNegativeButton(@StringRes int var1, OnClickListener var2);

        PermissionDialog.Builder setNegativeButton(CharSequence var1, OnClickListener var2);

        PermissionDialog.Builder setNeutralButton(@StringRes int var1, OnClickListener var2);

        PermissionDialog.Builder setNeutralButton(CharSequence var1, OnClickListener var2);

        PermissionDialog.Builder setCancelable(boolean var1);

        PermissionDialog.Builder setOnCancelListener(OnCancelListener var1);

        PermissionDialog.Builder setOnDismissListener(OnDismissListener var1);

        PermissionDialog.Builder setOnKeyListener(OnKeyListener var1);

        PermissionDialog.Builder setItems(@ArrayRes int var1, OnClickListener var2);

        PermissionDialog.Builder setItems(CharSequence[] var1, OnClickListener var2);

        PermissionDialog.Builder setAdapter(ListAdapter var1, OnClickListener var2);

        PermissionDialog.Builder setCursor(Cursor var1, OnClickListener var2, String var3);

        PermissionDialog.Builder setMultiChoiceItems(@ArrayRes int var1, boolean[] var2, OnMultiChoiceClickListener var3);

        PermissionDialog.Builder setMultiChoiceItems(CharSequence[] var1, boolean[] var2, OnMultiChoiceClickListener var3);

        PermissionDialog.Builder setMultiChoiceItems(Cursor var1, String var2, String var3, OnMultiChoiceClickListener var4);

        PermissionDialog.Builder setSingleChoiceItems(@ArrayRes int var1, int var2, OnClickListener var3);

        PermissionDialog.Builder setSingleChoiceItems(Cursor var1, int var2, String var3, OnClickListener var4);

        PermissionDialog.Builder setSingleChoiceItems(CharSequence[] var1, int var2, OnClickListener var3);

        PermissionDialog.Builder setSingleChoiceItems(ListAdapter var1, int var2, OnClickListener var3);

        PermissionDialog.Builder setOnItemSelectedListener(OnItemSelectedListener var1);

        PermissionDialog.Builder setView(int var1);

        PermissionDialog.Builder setView(View var1);

        PermissionDialog create();

        PermissionDialog show();
    }

    private static class Api20Dialog extends PermissionDialog
    {
        private android.support.v7.app.AlertDialog alertDialog;

        private Api20Dialog(android.support.v7.app.AlertDialog alertDialog)
        {
            this.alertDialog = alertDialog;
        }

        public void show()
        {
            this.alertDialog.show();
        }

        public void dismiss()
        {
            if (this.alertDialog.isShowing())
            {
                this.alertDialog.dismiss();
            }

        }

        public boolean isShowing()
        {
            return this.alertDialog.isShowing();
        }

        public void cancel()
        {
            if (this.alertDialog.isShowing())
            {
                this.alertDialog.cancel();
            }

        }

        public Button getButton(int whichButton)
        {
            return this.alertDialog.getButton(whichButton);
        }

        @Nullable
        public ListView getListView()
        {
            return this.alertDialog.getListView();
        }

        @NonNull
        public Context getContext()
        {
            return this.alertDialog.getContext();
        }

        @Nullable
        public View getCurrentFocus()
        {
            return this.alertDialog.getCurrentFocus();
        }

        @NonNull
        public LayoutInflater getLayoutInflater()
        {
            return this.alertDialog.getLayoutInflater();
        }

        @Nullable
        public Activity getOwnerActivity()
        {
            return this.alertDialog.getOwnerActivity();
        }

        public int getVolumeControlStream()
        {
            return this.alertDialog.getVolumeControlStream();
        }

        @Nullable
        public Window getWindow()
        {
            return this.alertDialog.getWindow();
        }
    }

    private static class Api21Dialog extends PermissionDialog
    {
        private android.app.AlertDialog alertDialogApp;

        private Api21Dialog(android.app.AlertDialog alertDialog)
        {
            this.alertDialogApp = alertDialog;
        }

        public void show()
        {
            this.alertDialogApp.show();
        }

        public void dismiss()
        {
            if (this.alertDialogApp.isShowing())
            {
                this.alertDialogApp.dismiss();
            }

        }

        public boolean isShowing()
        {
            return this.alertDialogApp.isShowing();
        }

        public void cancel()
        {
            if (this.alertDialogApp.isShowing())
            {
                this.alertDialogApp.cancel();
            }

        }

        public Button getButton(int whichButton)
        {
            return this.alertDialogApp.getButton(whichButton);
        }

        @Nullable
        public ListView getListView()
        {
            return this.alertDialogApp.getListView();
        }

        @NonNull
        public Context getContext()
        {
            return this.alertDialogApp.getContext();
        }

        @Nullable
        public View getCurrentFocus()
        {
            return this.alertDialogApp.getCurrentFocus();
        }

        @NonNull
        public LayoutInflater getLayoutInflater()
        {
            return this.alertDialogApp.getLayoutInflater();
        }

        @Nullable
        public Activity getOwnerActivity()
        {
            return this.alertDialogApp.getOwnerActivity();
        }

        public int getVolumeControlStream()
        {
            return this.alertDialogApp.getVolumeControlStream();
        }

        @Nullable
        public Window getWindow()
        {
            return this.alertDialogApp.getWindow();
        }
    }
}
