package com.titanic.webmvc.jdbc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DishController {

    private final DishRepository dishRepository;

    @GetMapping("/dish")
    public String dish() {
        Iterable<Dish> dishs = dishRepository.findAll();
        log.info("dish - {}", dishs);
        return "success";
    }
}
