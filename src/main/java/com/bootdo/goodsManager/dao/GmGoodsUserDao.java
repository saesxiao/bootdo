package com.bootdo.goodsManager.dao;

import com.bootdo.goodsManager.domain.GmGoodsUserDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 商品对应用户表
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-02 15:44:34
 */
@Mapper
public interface GmGoodsUserDao {

	GmGoodsUserDO get(Integer id);

	GmGoodsUserDO getByCode(String goodsCode,Long userId);
	
	List<GmGoodsUserDO> list(Map<String, Object> map);

	List<HashMap<String,Object>> listUser(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GmGoodsUserDO gmGoodsUser);
	
	int update(GmGoodsUserDO gmGoodsUser);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
