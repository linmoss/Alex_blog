package cn.Alex.test;

import org.Alex.Application;
import org.Alex.common.DefaultImage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageTest {
    @Autowired
    private DefaultImage defaultImage;


    @Test
    public void testCover(){
        System.out.println(defaultImage.cover(""));
        System.out.println(defaultImage.cover("111"));
    }

    @Test
    public void testAvatar(){
        System.out.println(defaultImage.avatar("3222722025@qq.com"));
        System.out.println(defaultImage.avatar("3222722025@qq11.com"));
    }
}
