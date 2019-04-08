package com.bootdo.testDemo;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.goodsManager.dao.GmGoodsUserDao;
import com.bootdo.goodsManager.service.GmGoodsUserService;
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


    @Test
    public void test() {
        redisTemplate.opsForValue().set("a", "b");
        System.out.println(redisTemplate.opsForValue().get("a"));
    }

    @Test
    public void testGoodsUserDao(){
        List<HashMap<String, Object>> res = goodsUserService.listUser(new HashMap<>());
        for (HashMap<String,Object> item:res) {
            item.entrySet().stream().forEach(temp->{
                System.out.print(temp.getKey()+" : "+temp.getValue()+",       ");
            });
            System.out.println();
        }
    }

    @Test
    public void test2() {
        JSONObject json = new JSONObject();
        json.put("娃哈哈",25.5);
        json.put("薯片","3.5");
        json.entrySet().stream().forEach(temp->{
            System.out.println(temp.getKey()+"    ;   "+temp.getValue());
        });
    }
}
