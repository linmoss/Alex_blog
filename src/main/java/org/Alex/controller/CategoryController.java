package org.Alex.controller;

import org.Alex.common.Result;
import org.Alex.enums.ResultEnum;
import org.Alex.pojo.Category;
import org.Alex.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Result<List<Category>> list() {
        return Result.success(service.list());
    }

    @GetMapping("/{id}")
    public Result<Category> detail(@PathVariable Integer id) {
        Category category = service.detail(id);
        if (Objects.isNull(category)) {
            return Result.error(ResultEnum.RESULT_DATA_NOT_FIND);
        } else {
            return Result.success(category);
        }
    }


    @PostMapping("/")
    public Result<String> save(Category category) {
        service.save(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }
}
