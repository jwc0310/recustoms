package com.andy.xyfloatingball.floating;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
@SuppressWarnings("ALL")
public class AppBean {

    /**
     * id : 143
     * name : 诛仙
     * iconUrl : http://www.andy.com.cn/new_market/img/zhuxian_logo.png
     * downloadUrl : http://www.andy.com.cn/new_market/apk/zhuxian_xiaoyao_ew1380111626.apk
     * packageName : com.wanmei.zhuxian.ewan.xyaz
     * apkSize : 416328154
     * downloadTimes : 680500
     * from : 官方
     */

    @SerializedName("apk")
    private List<AppInfo> apps;

    public List<AppInfo> getApps() {
        return apps;
    }

    public void setApps(List<AppInfo> apps) {
        this.apps = apps;
    }

    public static class AppInfo {
        private String id;
        private String name;
        private String iconUrl;
        private String downloadUrl;
        private String description;
        private String screenshotsUrl;
        private String packageName;
        private String apkSize;
        private String downloadTimes;
        private String from;
        private String versionName;
        private String versionCode;
        private int markid;
        private String downloadType;
        private String position = "-1"; //应用显示的位置
        private String module;
        private String userName; //发起下载用户
        private String downloadFrom; //发起下载来源

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getScreenshotsUrl() {
            return screenshotsUrl;
        }

        public void setScreenshotsUrl(String screenshotsUrl) {
            this.screenshotsUrl = screenshotsUrl;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getApkSize() {
            return apkSize;
        }

        public void setApkSize(String apkSize) {
            this.apkSize = apkSize;
        }

        public String getDownloadTimes() {
            return downloadTimes;
        }

        public void setDownloadTimes(String downloadTimes) {
            this.downloadTimes = downloadTimes;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public int getMarkid() {
            return markid;
        }

        public void setMarkid(int markid) {
            this.markid = markid;
        }

        public String getDownloadType() {
            return downloadType;
        }

        public void setDownloadType(String downloadType) {
            this.downloadType = downloadType;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDownloadFrom() {
            return downloadFrom;
        }

        public void setDownloadFrom(String downloadFrom) {
            this.downloadFrom = downloadFrom;
        }

        @Override
        public String toString() {
            return "AppInfo{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", apkSize='" + apkSize + '\'' +
                    ", downloadTimes='" + downloadTimes + '\'' +
                    ", from='" + from + '\'' +
                    '}';
        }
    }
}
