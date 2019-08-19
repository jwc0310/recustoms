package com.othershe.mdview.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class UpdateOp implements Serializable {

    /**
     * true: add
     * false: remove
     */
    private boolean op;
    private String pkg;

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    private Drawable drawable;
    private String appName;

    public boolean isOp() {
        return op;
    }

    public void setOp(boolean op) {
        this.op = op;
    }

    public UpdateOp(String pkg, boolean op, String appName, Drawable drawable) {
        this.pkg = pkg;
        this.op = op;
        this.appName = appName;
        this.drawable = drawable;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }


}
