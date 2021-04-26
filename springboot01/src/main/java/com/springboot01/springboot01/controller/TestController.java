package com.springboot01.springboot01.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {


    @RequestMapping("/{uuid}")
    public String test(@PathVariable String uuid){
        return uuid;
    }
}

