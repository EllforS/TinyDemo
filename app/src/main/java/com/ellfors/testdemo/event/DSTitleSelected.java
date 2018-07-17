package com.ellfors.testdemo.event;

/**
 * 我的证书-Title选择
 * 2018/7/17 11:12
 */
public class DSTitleSelected
{
    private int position;

    public DSTitleSelected(int position)
    {
        this.position = position;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }
}
