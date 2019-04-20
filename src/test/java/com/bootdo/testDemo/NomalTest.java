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
        Integer month = 1;
        Integer pastTwoMonth = month-2>0?month-2:12+(month-2);
        System.out.println(pastTwoMonth);
    }
}
