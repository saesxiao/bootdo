package com.bootdo.testDemo;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.HttpUtil;
import com.bootdo.goodsManager.dao.GmGoodsUserDao;
import com.bootdo.goodsManager.domain.GmGoodsUserDO;
import com.bootdo.goodsManager.domain.GmOrderDO;
import com.bootdo.goodsManager.service.GmGoodsUserService;
import com.bootdo.goodsManager.service.GmOrderService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    GmGoodsUserService goodsUserService;

    @Autowired
    UserService userService;

    @Autowired
    GmOrderService orderService;


    @Test
    public void test() {
        // 2019-04-20 18:02:43
        Map<String,Object> query = new HashMap<>();
        query.put("userId",16L);
        query.put("monthStart","2019-04-01 00:00:00");
        query.put("monthEnd","2019-04-30 23:29:59");
        List<GmOrderDO> list = orderService.list(query);
        for (GmOrderDO orderDO:list) {
            System.out.println(orderDO);
        }
    }
}
