package com.ellfors.testdemo.ext

interface ValueExt
{
    infix fun Int.add(value: Int): Int
    {
        return this + value
    }

    infix fun Int.reduce(value: Int): Int
    {
        return this - value
    }

}