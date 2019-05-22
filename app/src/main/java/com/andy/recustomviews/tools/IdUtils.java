package com.andy.recustomviews.tools;

/**
 * Created by Administrator on 2017/6/13.
 */
public class IdUtils {

    /**
     * 反射得到组件的id号
     * @param packageName 包名
     * @param className layout,string,drawable,style,id,color,array
     * @param idName    唯一文件名
     * @return  资源id
     */
    public static int getCompentID(String packageName, String className, String idName) {
        int id = 0;
        try {
            Class<?> cls = Class.forName(packageName + ".R$" + className);
            id = cls.getField(idName).getInt(cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
