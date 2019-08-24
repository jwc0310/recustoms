package com.examplepatterns.template;

import com.example.patterns.template.Abstract;

/**
 * 模板方法模式是类的行为模式
 * Created by Administrator on 2017/11/13.
 */

public class Concrete extends Abstract {

    @Override
    protected String setFormatUrl(String url) {
        return url+", class";
    }
}
