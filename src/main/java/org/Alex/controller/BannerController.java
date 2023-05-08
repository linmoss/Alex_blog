package org.Alex.controller;

import org.Alex.common.Result;
import org.Alex.enums.ResultEnum;
import org.Alex.pojo.Banner;
import org.Alex.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/admin/banner")
public class BannerController {

    private final BannerService service;

    public BannerController(BannerService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Result<List<Banner>> list() {
        return Result.success(service.list());
    }

    @GetMapping("/{id}")
    public Result<Banner> detail(@PathVariable Integer id) {
        Banner banner = service.detail(id);
        if (Objects.isNull(banner)) {
            return Result.error(ResultEnum.RESULT_DATA_NOT_FIND);
        } else {
            return Result.success(banner);
        }
    }


    @PostMapping("/")
    public Result<String> save(Banner banner) {
        service.save(banner);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }

    @PostMapping("/order")
    public Result<String> ordered(String ids) {
        Arrays.stream(ids.split(";")).map(x -> x.split("----")).forEach(x -> service.order(Integer.parseInt(x[0]), Integer.parseInt(x[1])));
        return Result.success();
    }
}
