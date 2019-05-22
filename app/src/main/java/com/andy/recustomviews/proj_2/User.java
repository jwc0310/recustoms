package com.andy.recustomviews.proj_2;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User
{
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", memberSex=" + memberSex +
                ", memberLastX='" + memberLastX + '\'' +
                ", memberNickname='" + memberNickname + '\'' +
                ", memberIcon='" + memberIcon + '\'' +
                ", memberMobile='" + memberMobile + '\'' +
                ", memberId=" + memberId +
                ", memberDetailAddr='" + memberDetailAddr + '\'' +
                '}';
    }

    @Id
    private String id;
    private int memberSex;//性别
    private String memberLastX;//X币
    @Property(nameInDb = "sex")
    private String memberNickname;//昵称
    private String memberIcon;//头像地址链接
    private String memberMobile;//手机号
    private int memberId;//用户ID
    @Transient
    private String memberDetailAddr;//用户的详细地址
    @Generated(hash = 277580624)
    public User(String id, int memberSex, String memberLastX, String memberNickname,
            String memberIcon, String memberMobile, int memberId) {
        this.id = id;
        this.memberSex = memberSex;
        this.memberLastX = memberLastX;
        this.memberNickname = memberNickname;
        this.memberIcon = memberIcon;
        this.memberMobile = memberMobile;
        this.memberId = memberId;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getMemberSex() {
        return this.memberSex;
    }
    public void setMemberSex(int memberSex) {
        this.memberSex = memberSex;
    }
    public String getMemberLastX() {
        return this.memberLastX;
    }
    public void setMemberLastX(String memberLastX) {
        this.memberLastX = memberLastX;
    }
    public String getMemberNickname() {
        return this.memberNickname;
    }
    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }
    public String getMemberIcon() {
        return this.memberIcon;
    }
    public void setMemberIcon(String memberIcon) {
        this.memberIcon = memberIcon;
    }
    public String getMemberMobile() {
        return this.memberMobile;
    }
    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }
    public int getMemberId() {
        return this.memberId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}