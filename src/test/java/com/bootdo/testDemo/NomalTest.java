package com.bootdo.testDemo;

import com.bootdo.common.utils.DateUtil;
import com.bootdo.common.utils.DateUtils;
import com.bootdo.common.utils.OrderTool;
import com.bootdo.common.utils.RandomCode;
import io.swagger.models.auth.In;
import org.junit.Test;

import java.util.Random;

public class NomalTest {

    @Test
    public void test(){
        String orderCode = "XD"+ OrderTool.getOrderNo(5);
        System.out.println(orderCode);
    }
    @Test
    public void test2(){
        System.out.println(RandomCode.toSerialCode(2));
    }

    @Test
    public void test3(){
        Integer today = Integer.parseInt(DateUtil.getDay());
        Integer month = 12;
        Integer year = Integer.parseInt(DateUtil.getYear());
        Integer userLevel = 2;
        Integer pastMonth = userLevel==2?3:2;

        // 获取本月一号 和两个月前的一号
        Integer pastTwoMonth = month-pastMonth>0?month-pastMonth:12+(month-pastMonth);
        Integer pastYear = month-pastMonth>0?year:year-1;
        String monthStart = pastYear + "-" + (pastTwoMonth<10?"0"+pastTwoMonth:pastTwoMonth) + "-" + "01 00:00:00";
        String monthEnd = DateUtil.getFirstDayOfMonth()+" 00:00:00";
        System.out.println(monthStart);
        System.out.println(monthEnd);
    }
}
