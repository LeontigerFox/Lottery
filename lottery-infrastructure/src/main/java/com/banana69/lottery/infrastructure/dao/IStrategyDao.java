package com.banana69.lottery.infrastructure.dao;

import com.banana69.lottery.infrastructure.po.Strategy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.

 * @author: banana69
 * @date: 2023/4/11/21:28
 * @description: 策略配置
 */
@Mapper
public interface IStrategyDao extends BaseMapper<Strategy> {
}
