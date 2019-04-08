package com.bootdo.testDemo;

import com.bootdo.common.utils.OrderTool;
import org.junit.Test;

public class NomalTest {

    @Test
    public void test(){
        String orderCode = "XD"+ OrderTool.getOrderNo(5);
        System.out.println(orderCode);
    }
}
