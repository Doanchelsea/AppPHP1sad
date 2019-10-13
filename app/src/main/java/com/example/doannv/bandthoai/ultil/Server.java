package com.example.doannv.bandthoai.ultil;

public class Server {
    // mang banh mi
      // public static String localhost = "192.168.88.124";
    // mang tieng anh
//    public static String localhost = "10.255.157.106";
    // mạng nhà
    public static String localhost = "192.168.1.6";
     public static String duongdanloaisp = "http://"+ localhost +"/server/getloaisp.php";
     public static String duongdansanphammoinhat = "http://"+ localhost +"/server/getsanphammoinhat.php";
    public static String duongdandienthoai = "http://"+ localhost +"/server/getsanpham.php?page=";
    public static String duongdanlaptop = "http://"+ localhost +"/server/laptop.php?page=";
    public static String duongdandonhang = "http://"+ localhost +"/server/thongtinkhachang.php";
}