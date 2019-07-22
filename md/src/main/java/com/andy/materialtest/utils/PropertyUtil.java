package com.andy.materialtest.utils;

import java.lang.reflect.Method;

public class PropertyUtil {

    public static String getProperty(String key, String defValue) {
        String value = "";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, defValue));
            if (!value.matches("([A-Z]|[a-z]|[0-9]|[:]|[-]){0,}")) {
                value = defValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
