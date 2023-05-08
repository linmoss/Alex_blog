package org.Alex.config;

import org.Alex.pojo.Category;
import org.Alex.service.CategoryService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryService categoryService;

    public ContextRefreshedListener(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (categoryService.count() == 0) {
            categoryService.save(Category.builder().name("默认分类").summary("这是一个默认分类").build());
        }
    }
}