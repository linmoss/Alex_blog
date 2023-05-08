package cn.Alex.test;

import org.Alex.Application;
import org.Alex.common.MailHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailTest {
    @Autowired
    private MailHelper helper;

    @Test
    public void testSend(){
        helper.sendMail("nxnn33@163.com","测试邮件","<h1>Alex</h1><p>我是一个测试邮件</p>");
    }
}
