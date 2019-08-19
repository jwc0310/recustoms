package com.othershe.mdview.bean;

public class UpdateInfo {

    private String pkg;
    private boolean isIn;

    public UpdateInfo(String pkg, boolean isIn) {
        this.pkg = pkg;
        this.isIn = isIn;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        isIn = in;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
}
