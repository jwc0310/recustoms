package com.example.patterns.template;

/**
 * ģ�巽��ģʽ�������Ϊģʽ
 * Created by Administrator on 2017/11/13.
 */

public class Concrete extends Abstract {

    @Override
    protected String setFormatUrl(String url) {
        return url+", class";
    }
}
