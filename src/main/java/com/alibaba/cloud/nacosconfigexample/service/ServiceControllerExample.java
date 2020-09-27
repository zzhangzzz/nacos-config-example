package com.alibaba.cloud.nacosconfigexample.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/9/27 4:55 下午
 * info :
 */
@RestController
public class ServiceControllerExample {

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message) {
        return "[ECHO]" + message;
    }

}
