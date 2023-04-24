package com.banana69.lottery.domain.award.service.factory;

import com.banana69.lottery.common.Constants;
import com.banana69.lottery.domain.award.service.goods.IDistributionGoods;
import com.banana69.lottery.domain.award.service.goods.impl.CouponGoods;
import com.banana69.lottery.domain.award.service.goods.impl.DescGoods;
import com.banana69.lottery.domain.award.service.goods.impl.PhysicalGoods;
import com.banana69.lottery.domain.award.service.goods.impl.RedeemCodeGoods;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/11/20:43
 * @description: 各类发奖奖品配置类
 */
public class GoodsConfig {

    /* 奖品发放策略组 */
    protected static Map<Integer, IDistributionGoods> goodsMap = new ConcurrentHashMap<>();

    @Resource
    private DescGoods descGoods;

    @Resource
    private RedeemCodeGoods redeemCodeGoods;

    @Resource
    private CouponGoods couponGoods;

    @Resource
    private PhysicalGoods physicalGoods;

    @PostConstruct
    public void init() {
        goodsMap.put(Constants.AwardType.DESC.getCode(), descGoods);
        goodsMap.put(Constants.AwardType.RedeemCodeGoods.getCode(), redeemCodeGoods);
        goodsMap.put(Constants.AwardType.CouponGoods.getCode(), couponGoods);
        goodsMap.put(Constants.AwardType.PhysicalGoods.getCode(), physicalGoods);
    }


}
