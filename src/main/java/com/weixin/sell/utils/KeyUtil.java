package com.weixin.sell.utils;

import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式 ： 时间+随机数
     *
     * synchronized 同步锁 ，防止多线程并发时有重复
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random=new Random();

        Integer number=random.nextInt(900000)+100000;//生成6位随机数

        return System.currentTimeMillis()+String.valueOf(number);
    }
}
