package org.Alex.controller;

import org.Alex.common.ArticleSearch;
import org.Alex.common.PageHelper;
import org.Alex.common.Result;
import org.Alex.enums.ResultEnum;
import org.Alex.pojo.Article;
import org.Alex.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/admin/article")
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Result<List<Article>> list() {
        return Result.success(service.list());
    }

    @GetMapping("/{id}")
    public Result<Article> detail(@PathVariable Integer id) {
        Article article = service.detail(id);
        if (Objects.isNull(article)) {
            return Result.error(ResultEnum.RESULT_DATA_NOT_FIND);
        } else {
            return Result.success(article);
        }
    }


    @PostMapping("/")
    public Result<String> save(@RequestBody Article article) {
        service.save(article);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }

    @GetMapping("/search")
    public Result<PageHelper<Article>> search(ArticleSearch search) {
        search.setPageNum(Objects.isNull(search.getPageNum()) || search.getPageNum() < 1 ? 1 : search.getPageNum());
        search.setPageSize(Objects.isNull(search.getPageSize()) || search.getPageSize() < 1 ? 1 : search.getPageSize());
        PageHelper<Article> pageHelper = service.search(search);
        return Result.success(pageHelper);
    }
}
