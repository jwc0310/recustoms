package com.example.patterns.template;

/**
 * ģ�巽��ģʽ�������Ϊģʽ
 * Created by Administrator on 2017/11/13.
 */

public abstract class Abstract {

    /**
     * �����ķ���
     **/
    public final void print(String url) {
        System.out.println(setFormatUrl(url));
    }

    /**
     * �Զ��巽��
     **/
    protected abstract String setFormatUrl(String url);

}
