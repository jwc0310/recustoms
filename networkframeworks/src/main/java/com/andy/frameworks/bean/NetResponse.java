package com.andy.frameworks.bean;
/*
{
        data: {},
        msg: "上传成功",
        code: 200
        }
*/
public class NetResponse<T> {
    public int code;
    public String msg;
    public T data;
}
