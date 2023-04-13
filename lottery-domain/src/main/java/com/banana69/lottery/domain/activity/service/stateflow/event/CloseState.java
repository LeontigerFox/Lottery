package com.banana69.lottery.domain.activity.service.stateflow.event;

import com.banana69.lottery.common.Constants;
import com.banana69.lottery.common.Result;
import com.banana69.lottery.domain.activity.service.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/12/14:52
 * @description: 活动关闭状态
 */
@Component
public class CloseState  extends AbstractState {
    @Override
    public Result arraignment(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动关闭不可提审");
    }

    @Override
    public Result checkPass(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动关闭不可审核通过");
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动关闭不可审核拒绝");
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动关闭不可撤销审核");
    }

    @Override
    public Result close(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动关闭不可重复关闭");
    }

    @Override
    public Result open(Long activityId, Enum<Constants.ActivityState> currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, Constants.ActivityState.OPEN);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动开启完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result doing(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动关闭不可变更活动中");
    }

}
