package com.andy.recustomviews.java.javatips;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/10/16.
 */

public class compare {

    public static void main(String[] args) {

    }

    /**
     * 支持排序
     * 内部比较器
     *
     */
    public class ComprableImpl implements Comparable<Integer>{

        public Integer cc;
        public ComprableImpl(Integer b){
            cc = b;
        }

        @Override
        public int compareTo(@NonNull Integer o) {
            return cc.compareTo(o);
        }
    }

    /**
     * 外部排序
     */
    public class ComparatorImpl implements Comparator<ComprableImpl> {

        @Override
        public int compare(ComprableImpl o1, ComprableImpl o2) {
            return  o1.cc - o2.cc;
        }
    }

}
