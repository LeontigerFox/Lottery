package com.banana69.lottery.domain.support.ids.policy;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.banana69.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/13/17:02
 * @description: hutool 工具包下的雪花算法
 *      15位雪花算法推荐：https://github.com/yitter/idgenerator/blob/master/Java/source/src/main/java/com/github/yitter/core/SnowWorkerM1.java
 */
@Component
public class SnowFlake implements IIdGenerator {

    private Snowflake snowflake;

    @PostConstruct
    public void init() {
        // 0 ~ 31 位 采用配置的方法使用
        long workerId;
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        }catch (Exception e) {
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
        workerId = workerId >> 16 & 31;

        long dataCenterId = 1L;
        snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
    }

    @Override
    public long nextId() {
        return snowflake.nextId();
    }
}
