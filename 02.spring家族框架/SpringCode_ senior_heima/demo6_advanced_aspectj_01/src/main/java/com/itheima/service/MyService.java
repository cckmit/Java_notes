package com.itheima.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// 待增强的目标类
@Service
public class MyService {

    private static final Logger log = LoggerFactory.getLogger(MyService.class);

    public static void foo() {
        log.debug("foo()");
    }
}
