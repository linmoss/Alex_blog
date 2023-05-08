package cn.Alex.test;

import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;


public class MD5Test {
    @Test
    public void test(){
        String pass = DigestUtils.md5DigestAsHex("yxj6558066".getBytes(StandardCharsets.UTF_8));
        System.out.println(pass);
    }
}
