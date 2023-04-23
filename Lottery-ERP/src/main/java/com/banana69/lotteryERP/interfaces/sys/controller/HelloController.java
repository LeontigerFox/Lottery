package com.banana69.lotteryERP.interfaces.sys.controller;

import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/22/16:46
 * @description:
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    @PreAuthorize("hasAnyAuthority('system:super:list')")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/testCors")
    public EasyResult<?> testCors(){
        return EasyResult.success(200,"testCors");
    }
}
