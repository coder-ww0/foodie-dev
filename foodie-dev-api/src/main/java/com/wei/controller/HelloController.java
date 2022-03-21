package com.wei.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author www
 * @date 2022/3/21 17:15
 * @description: TODO
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Object hello() {
        return "Hello Spring";
    }
}
