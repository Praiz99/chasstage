package com.wckj.chasstage.common.util.pdf.entity;


public class DataEntity
{

    public DataEntity(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    private String name;
    private String value;
}
