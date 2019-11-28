package com.zhou.util;

import com.zhou.vo.ResultVO;

import java.util.Arrays;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 19:27
 */
public class ResultUtil {

    public static ResultVO success(Object o) {
        return new ResultVO().setCode(0).setData(o).setMsg("成功");
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        return new ResultVO().setCode(code).setMsg(msg);
    }


    public static void main(String[] args) {
        System.out.println(new ResultVO().setCode(1).setMsg("ss").setData(Arrays.asList("1","2","3")));

        System.out.println(new ResultVO().setCode(1).setMsg("ss").setData(Arrays.asList("1","2","3")));
    }
}
