package com.banana69.lottery.infrastructure.repository;

import com.banana69.lottery.domain.award.repository.IOrderRepository;
import com.banana69.lottery.infrastructure.dao.IUserStrategyExportDao;
import com.banana69.lottery.infrastructure.po.UserStrategyExport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/12/13:47
 * @description: 奖品表仓储服务
 */
@Component
public class OrderRepository implements IOrderRepository {
    @Resource
    private IUserStrategyExportDao userStrategyExportDao;
    @Override
    public void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setAwardId(awardId);
        userStrategyExport.setGrantState(grantState);
        userStrategyExportDao.updateUserAwardState(userStrategyExport);

    }
}
