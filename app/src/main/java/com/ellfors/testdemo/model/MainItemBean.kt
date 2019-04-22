package com.ellfors.testdemo.model

class MainItemBean(val id: ID, val text: String)
{
    enum class ID
    {
        RIGHT_GRID,
        REFRESH,
        STATUS_BAR,
        TINKER,
        EDIT_TEXT,
        BLUR,
        YSPAY,
        BUILD,
        SWIPE_BACK,
        NAVIGATION,
        DRAWER,
        TOUCH_LIST
    }
}