package com.py;

import org.apache.shiro.crypto.hash.Md5Hash;

public class TestShiroMD5 {
    public static void main(String[] args) {
        //创建一个md5算法
        Md5Hash md5Hash = new Md5Hash();
        md5Hash.setBytes("123".getBytes());
        String s = md5Hash.toHex();
        System.out.println(s);//313233

        //使用md5
        Md5Hash md5Hash1 = new Md5Hash("123");
        System.out.println(md5Hash1.toHex());//202cb962ac59075b964b07152d234b70

        //使用md5 + salt处理
        Md5Hash md5Hash2 = new Md5Hash("123","X0*7ps");
        System.out.println(md5Hash2.toHex());//8a83592a02263bfe6752b2b5b03a4799

        //使用md5 + salt + hash散列
        Md5Hash md5Hash3 = new Md5Hash("123","X0*7ps",1024);
        System.out.println(md5Hash3.toHex());//e4f9bf3e0c58f045e62c23c533fcf633
    }
}
