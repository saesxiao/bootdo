package com.bootdo.testDemo;

import com.bootdo.common.utils.OrderTool;
import com.bootdo.common.utils.RandomCode;
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
}
