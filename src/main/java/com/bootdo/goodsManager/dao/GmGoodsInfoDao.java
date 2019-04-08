package com.bootdo.goodsManager.dao;

import com.bootdo.goodsManager.domain.GmGoodsInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 商品信息表
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-02 15:44:34
 */
@Mapper
public interface GmGoodsInfoDao {

	GmGoodsInfoDO get(Integer id);
	
	List<GmGoodsInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GmGoodsInfoDO gmGoodsInfo);
	
	int update(GmGoodsInfoDO gmGoodsInfo);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
