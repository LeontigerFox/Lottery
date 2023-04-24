package com.banana69.lotteryERP.interfaces.lottery.controller;

import com.banana69.lotteryERP.domain.activity.model.ActivityListPageReq;
import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.lottery.dto.Activity;
import com.banana69.lotteryERP.interfaces.lottery.service.IActivityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/17:43
 * @description: 活动管理
 */
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

    @Resource
    private IActivityService activityService;

    @RequestMapping("/index")
    public String index() {
        System.out.println("success");
        return "index";
    }

    @RequestMapping("/queryActivityListPage")
    @ResponseBody
    public EasyResult<?> queryActivityListPage(String page, String rows) {
        try {
            log.info("查询活动列表数据 page：{} rows：{}", page, rows);
            rows = null == rows ? "10" : rows;
            ActivityListPageReq req = new ActivityListPageReq(page, rows);
            req.setErpId("admin");
            return activityService.queryActivityListPage(req);
        } catch (Exception e) {
            log.error("查询活动列表数据失败 page：{} rows：{}", page, rows, e);
            return EasyResult.fail(String.valueOf(e));
        }
    }

    /**
     * 查询所有活动
     * @return
     */
    @GetMapping("/all")
    public EasyResult<List<Activity>> getAllUsers() {
        List<Activity> list = activityService.list();
        return  EasyResult.success(list);
    }

    @GetMapping("/list")
    public EasyResult<Map<String, Object>> getActivityInfo(@RequestParam(value = "activityId",required = false) Long activityId,
                                                       @RequestParam(value = "activityName",required = false) String activityName,
                                                       @RequestParam(value = "creator",required = false) String creator,
                                                       @RequestParam(value = "pageNo",required = false) Long pageNo,
                                                       @RequestParam(value = "pageSize",required = false) Long pageSize){
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.checkValNotNull(activityId), Activity::getActivityId, activityId);
        wrapper.eq(StringUtils.checkValNotNull(activityName), Activity::getActivityName, activityName);
        wrapper.eq(StringUtils.checkValNotNull(creator), Activity::getCreator, creator);
        wrapper.orderByAsc(Activity::getActivityId);

        Page<Activity> page = new Page<>(pageNo,pageSize);
        activityService.page(page,wrapper);

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("total", page.getTotal());
        data.put("rows",page.getRecords());

        return EasyResult.success(data);
    }


}
