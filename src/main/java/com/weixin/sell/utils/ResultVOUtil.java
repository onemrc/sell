package com.weixin.sell.utils;

import com.weixin.sell.enums.ProductStatusEnum;
import com.weixin.sell.vo.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(data);
        resultVO.setCode(ProductStatusEnum.UP.getCode());
        resultVO.setMsg("成功");

        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
