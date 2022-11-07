package com.example.firebase1;


public class User {
    private String ten;
    private String ngaysinh;
    private String sdt;
    private String email;
    private String cmnd;
    private String username;
    private String password;
    private String diachi;
    private byte[] image;
    public Boolean checkfinger;

    public User(String username,String ten, String password) {
        this.ten = ten;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Tên: "+ten+"\nMã sinh viên: "+username;
    }

    public User() {
    }

    public Boolean getCheckfinger() {
        return checkfinger;
    }

    public void setCheckfinger(Boolean checkfinger) {
        this.checkfinger = checkfinger;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public User(String ten, String ngaysinh, String sdt, String email, String cmnd, String username, String password, byte[] image, String diachi) {
        this.ten = ten;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.email = email;
        this.cmnd = cmnd;
        this.username = username;
        this.password = password;
        this.image = image;
        this.diachi=diachi;
    }

    public User(String ten, String ngaysinh, String sdt, String email, String cmnd, String username, String password) {
        this.ten = ten;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.email = email;
        this.cmnd = cmnd;
        this.username = username;
        this.password = password;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getTen() {
        return ten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public String getEmail() {
        return email;
    }

    public String getCmnd() {
        return cmnd;
    }
}

