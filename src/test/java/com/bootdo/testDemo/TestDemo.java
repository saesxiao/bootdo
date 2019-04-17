//package com.bootdo.testDemo;
//
//import com.alibaba.fastjson.JSONObject;
//import com.bootdo.common.utils.HttpUtil;
//import com.bootdo.goodsManager.dao.GmGoodsUserDao;
//import com.bootdo.goodsManager.domain.GmGoodsUserDO;
//import com.bootdo.goodsManager.service.GmGoodsUserService;
//import com.bootdo.system.domain.UserDO;
//import com.bootdo.system.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController()
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TestDemo {
//    @Autowired
//    RedisTemplate redisTemplate;
//
//    @Autowired
//    GmGoodsUserService goodsUserService;
//
//    @Autowired
//    UserService userService;
//
//
//    @Test
//    public void test() {
//        redisTemplate.opsForValue().set("a", "b");
//        System.out.println(redisTemplate.opsForValue().get("a"));
//    }
//
//    @Test
//    public void testGoodsUserDao(){
//        List<HashMap<String, Object>> res = goodsUserService.listUser(new HashMap<>());
//        for (HashMap<String,Object> item:res) {
//            item.entrySet().stream().forEach(temp->{
//                System.out.print(temp.getKey()+" : "+temp.getValue()+",       ");
//            });
//            System.out.println();
//        }
//    }
//
//    @Test
//    public void test2() {
//        JSONObject json = new JSONObject();
//        json.put("娃哈哈",25.5);
//        json.put("薯片","3.5");
//        json.entrySet().stream().forEach(temp->{
//            System.out.println(temp.getKey()+"    ;   "+temp.getValue());
//        });
//    }
//    @Test
//    public void test3() {
//        JSONObject goodsInfo = new JSONObject();
//        goodsInfo.put("1",5);
//        goodsInfo.put("3",7);
//        JSONObject param = new JSONObject();
//        param.put("goods",goodsInfo);
//        param.put("address","兰州");
//        param.put("imgUrl","/img");
//        Map<String, String> query = new HashMap<>();
//        query.put("jsonStr",param.toJSONString());
//        String response  = HttpUtil.post("http://127.0.0.1:8099/goodsManager/gmOrder/saveOrder",query);
//        System.out.println(response);
//    }
//    @Test
//    public void test4(){
//        Map<String,Object> query = new HashMap<>();
//        query.put("userId",2);
//        query.put("status",0);
//        query.put("type",2);
//        List<GmGoodsUserDO> list =goodsUserService.list(query);
//        for (GmGoodsUserDO gmGoodsUserDao:list) {
//            System.out.println(gmGoodsUserDao);
//        }
//    }
//
//    @Test
//    public void test5(){
//        Map<String,Object> query = new HashMap<>();
//        query.put("parentId",1);
//        List<UserDO> userList = userService.list(query);
//        System.out.println(userList.size());
//    }
//}
