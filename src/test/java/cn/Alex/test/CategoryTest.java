package cn.Alex.test;

import org.Alex.Application;
import org.Alex.mapper.CategoryMapper;
import org.Alex.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryTest {

    @Autowired
    private CategoryMapper mapper;

    @Test
    public void newEntity() {
        Category c1 = Category.builder().id(1).name("xx").summary("Qqq").build();
        System.out.println(c1);
    }


    @Test
    public void testMapper() {
        System.out.println(mapper);
    }

    @Test
    public void testUpdate() {
        Category category = mapper.findById(1).orElse(null);
        System.out.println(category);
        category.setName("新分类");
        mapper.save(category);
        System.out.println(mapper.findById(1));
    }

    @Test
    public void testAdd() {
        //save 如果这个对象的id不为null，它就是一个update，反之就是一个insert
        for (int i = 10; i < 20; i++) {
            mapper.save(Category.builder().name("测试分类" + i).summary("我是一个描述字段" + i + "...").build());
        }

    }

    @Test
    public void testFind() {
        System.out.println(mapper.findAll());

        System.out.println(mapper.findById(1));

    }
}
