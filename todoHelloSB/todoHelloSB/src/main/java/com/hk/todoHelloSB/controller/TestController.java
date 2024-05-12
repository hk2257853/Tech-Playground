package com.hk.todoHelloSB.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class TestController {
    @GetMapping
    public String TestPoint() {
        return "Hello Spring Boi";
    }
}
