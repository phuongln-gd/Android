package com.example.worker.model;

import java.util.Date;

public class CongViec {
    private String ten,desc;
    private String gioitinh;
    private String date;

    public CongViec() {
    }

    public CongViec(String ten, String desc, String gioitinh, String date) {
        this.ten = ten;
        this.desc = desc;
        this.gioitinh = gioitinh;
        this.date = date;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
