package com.example.exam.controller;


import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
// view로 연결하기 위한 함수만 정의
    @GetMapping("hello")
    // http://localhost:8080/hello
    public String Hello(Model model){
        // 값을 view로 전송하기 위해서 Model 필요
        model.addAttribute("name","홍길동");
        return "hello";
        //mustache의 파일명
    }
}
