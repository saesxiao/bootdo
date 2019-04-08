package com.bootdo.goodsManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.goodsManager.dao.GmGoodsUserDao;
import com.bootdo.goodsManager.domain.GmGoodsUserDO;
import com.bootdo.goodsManager.service.GmGoodsUserService;



@Service
public class GmGoodsUserServiceImpl implements GmGoodsUserService {
	@Autowired
	private GmGoodsUserDao gmGoodsUserDao;
	
	@Override
	public GmGoodsUserDO get(Integer id){
		return gmGoodsUserDao.get(id);
	}
	
	@Override
	public List<GmGoodsUserDO> list(Map<String, Object> map){
		return gmGoodsUserDao.list(map);
	}

	@Override
	public List<HashMap<String, Object>> listUser(Map<String, Object> map) {
		return gmGoodsUserDao.listUser(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return gmGoodsUserDao.count(map);
	}
	
	@Override
	public int save(GmGoodsUserDO gmGoodsUser){
		return gmGoodsUserDao.save(gmGoodsUser);
	}
	
	@Override
	public int update(GmGoodsUserDO gmGoodsUser){
		return gmGoodsUserDao.update(gmGoodsUser);
	}
	
	@Override
	public int remove(Integer id){
		return gmGoodsUserDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return gmGoodsUserDao.batchRemove(ids);
	}
	
}
