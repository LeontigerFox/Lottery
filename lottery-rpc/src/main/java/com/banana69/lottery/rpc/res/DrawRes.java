package com.banana69.lottery.rpc.res;

import java.io.Serializable;

import com.banana69.lottery.common.Result;
import com.banana69.lottery.rpc.dto.AwardDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.

 * @author: banana69
 * @date: 2023/4/19/16:18
 * @description:  抽奖结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawRes extends Result implements Serializable{

    private AwardDTO awardDTO;

    public DrawRes(String code, String info) {
        super(code, info);
    }


}
