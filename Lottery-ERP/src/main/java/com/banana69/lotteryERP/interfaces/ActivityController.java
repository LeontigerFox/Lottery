package com.banana69.lotteryERP.interfaces;

import com.banana69.lotteryERP.application.IActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/17:43
 * @description: 活动管理
 */
@Controller
@RequestMapping("api/activity")
@Slf4j
public class ActivityController {

    @Resource
    private IActivityService activityService;

    @RequestMapping("index")
    public String index() {
        System.out.println("success");
        return "index";
    }

}
