package com.cshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/redirect")
public class RedirectController {
    @RequestMapping(value = "/back")
    public String back(@RequestHeader(value = "referer", required = false) String referer) {
        if (!StringUtils.isEmpty(referer)) {
            return "redirect:" + referer;
        }
        return "/seckill-index.html";
    }
}
