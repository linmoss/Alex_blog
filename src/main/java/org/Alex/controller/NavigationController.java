package org.Alex.controller;

import org.Alex.common.Result;
import org.Alex.enums.ResultEnum;
import org.Alex.pojo.Navigation;
import org.Alex.service.NavigationService;
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
@RequestMapping("/admin/navigation")
public class NavigationController {

    private final NavigationService service;

    public NavigationController(NavigationService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Result<List<Navigation>> list() {
        return Result.success(service.list());
    }

    @GetMapping("/{id}")
    public Result<Navigation> detail(@PathVariable Integer id) {
        Navigation navigation = service.detail(id);
        if (Objects.isNull(navigation)) {
            return Result.error(ResultEnum.RESULT_DATA_NOT_FIND);
        } else {
            return Result.success(navigation);
        }
    }


    @PostMapping("/")
    public Result<String> save(Navigation navigation) {
        service.save(navigation);
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
