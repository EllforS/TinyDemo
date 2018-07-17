package com.ellfors.testdemo.ext

import android.widget.ImageView
import com.ellfors.testdemo.util.ImageLoader

interface ImageExt
{
    fun ImageView.displayImg(source: Any)
    {
        when (source)
        {
            is String -> ImageLoader.load(context, source, this)
            is Int -> this.setImageResource(source)
            else -> this.setImageResource(0)
        }
    }
}