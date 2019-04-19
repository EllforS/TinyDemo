package com.ellfors.testdemo.biz.drawer;

/**
 * TestBean
 * 2019/4/3 11:23
 */
public class TestBean
{
    private TestA valueA;
    private String name;
    private int age;

    public static class TestA
    {
        private TestB valueB;

        public TestB getValueB()
        {
            return valueB;
        }

        public void setValueB(TestB valueB)
        {
            this.valueB = valueB;
        }
    }

    public static class TestB
    {
        private TestC valueC;

        public TestC getValueC()
        {
            return valueC;
        }

        public void setValueC(TestC valueC)
        {
            this.valueC = valueC;
        }
    }

    public static class TestC
    {
        private String name;

        public String getName()
        {
            return name == null ? "" : name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
    }

    public TestA getValueA()
    {
        return valueA;
    }

    public void setValueA(TestA valueA)
    {
        this.valueA = valueA;
    }

    public String getName()
    {
        return name == null ? "" : name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }
}
