package com.example.doannv.bandthoai.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int ID;
    public String TenSanPham;
    public Integer GiaSanPham;
    public String Hinhanhsanpham;
    public String Motasanpham;
    public int IDsanpham;

    public Sanpham(int ID, String tenSanPham, Integer giaSanPham, String hinhanhsanpham, String motasanpham, int IDsanpham) {
        this.ID = ID;
        TenSanPham = tenSanPham;
        GiaSanPham = giaSanPham;
        Hinhanhsanpham = hinhanhsanpham;
        Motasanpham = motasanpham;
        this.IDsanpham = IDsanpham;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public Integer getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(Integer giaSanPham) {
        GiaSanPham = giaSanPham;
    }

    public String getHinhanhsanpham() {
        return Hinhanhsanpham;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        Hinhanhsanpham = hinhanhsanpham;
    }

    public String getMotasanpham() {
        return Motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        Motasanpham = motasanpham;
    }

    public int getIDsanpham() {
        return IDsanpham;
    }

    public void setIDsanpham(int IDsanpham) {
        this.IDsanpham = IDsanpham;
    }
}
