package org.Alex.controller;

import org.Alex.common.Result;
import org.Alex.enums.ResultEnum;
import org.Alex.pojo.Tag;
import org.Alex.service.TagService;
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
@RequestMapping("/admin/tag")
public class TagController {

    private final TagService service;

    public TagController(TagService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Result<List<Tag>> list() {
        return Result.success(service.list());
    }

    @GetMapping("/{id}")
    public Result<Tag> detail(@PathVariable Integer id) {
        Tag tag = service.detail(id);
        if (Objects.isNull(tag)) {
            return Result.error(ResultEnum.RESULT_DATA_NOT_FIND);
        } else {
            return Result.success(tag);
        }
    }


    @PostMapping("/")
    public Result<String> save(Tag tag) {
        service.save(tag);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }
}
