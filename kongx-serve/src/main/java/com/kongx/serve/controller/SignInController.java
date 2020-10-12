package com.kongx.serve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author raoxiaoyan
 */
@Controller
public class SignInController {

    @GetMapping("/index")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {
        return "index.html";
    }
}
