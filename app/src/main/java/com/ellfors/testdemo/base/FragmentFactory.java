package com.ellfors.testdemo.base;

import android.util.SparseArray;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * FragmentFactory
 * 2018/3/26 17:32
 */
public class FragmentFactory
{

    private SparseArray<BaseFragment> mFragmentCache = new SparseArray<BaseFragment>();

    public FragmentFactory()
    {
    }

    @SuppressWarnings("unchecked")
    public synchronized <T extends BaseFragment> T getFragment(Class<T> clazz, boolean useCache)
    {
        BaseFragment obj = mFragmentCache.get(clazz.hashCode());
        if (obj == null || !useCache)
        {
            try
            {
                Constructor<T> constructor = clazz
                        .getDeclaredConstructor();
                constructor.setAccessible(true);//访问私有成员方法，这句很关键
                T result = constructor.newInstance();
                if (useCache)
                {
                    mFragmentCache.put(clazz.hashCode(), result);
                }
                return result;
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
            throw new RuntimeException(String.format("Can not init fragment : %s", clazz));
        }
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public synchronized BaseFragment getBaseFragment(Class<?> clazz, boolean useCache)
    {
        BaseFragment obj = mFragmentCache.get(clazz.hashCode());
        if (obj == null || !useCache)
        {
            try
            {
                Constructor<?> constructor = clazz
                        .getDeclaredConstructor();
                constructor.setAccessible(true);//访问私有成员方法，这句很关键
                BaseFragment result = (BaseFragment) constructor.newInstance();
                if (useCache)
                {
                    mFragmentCache.put(clazz.hashCode(), result);
                }
                return result;
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
            throw new RuntimeException(String.format("Can not init fragment : %s", clazz));
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseFragment> T getFragmentFromCache(Class<T> clazz)
    {
        BaseFragment fragment = mFragmentCache.get(clazz.hashCode());
        return (T) fragment;
    }

    public <T extends BaseFragment> void removeFragmentFromCache(Class<T> clazz)
    {
        mFragmentCache.remove(clazz.hashCode());
    }
}
