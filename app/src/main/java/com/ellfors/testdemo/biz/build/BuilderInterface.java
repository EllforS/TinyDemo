package com.ellfors.testdemo.biz.build;

/**
 * BuilderInterface
 * 2018/12/25 18:07
 */
public interface BuilderInterface
{
    BuilderInterface setValue_1(String value);

    BuilderInterface setValue_2(int value);

    BuilderInterface setValue_3(boolean value);

    void toast();
}
