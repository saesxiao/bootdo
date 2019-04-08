package com.bootdo.goodsManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.goodsManager.dao.GmGoodsInfoDao;
import com.bootdo.goodsManager.domain.GmGoodsInfoDO;
import com.bootdo.goodsManager.service.GmGoodsInfoService;



@Service
public class GmGoodsInfoServiceImpl implements GmGoodsInfoService {
	@Autowired
	private GmGoodsInfoDao gmGoodsInfoDao;
	
	@Override
	public GmGoodsInfoDO get(Integer id){
		return gmGoodsInfoDao.get(id);
	}
	
	@Override
	public List<GmGoodsInfoDO> list(Map<String, Object> map){
		return gmGoodsInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return gmGoodsInfoDao.count(map);
	}
	
	@Override
	public int save(GmGoodsInfoDO gmGoodsInfo){
		return gmGoodsInfoDao.save(gmGoodsInfo);
	}
	
	@Override
	public int update(GmGoodsInfoDO gmGoodsInfo){
		return gmGoodsInfoDao.update(gmGoodsInfo);
	}
	
	@Override
	public int remove(Integer id){
		return gmGoodsInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return gmGoodsInfoDao.batchRemove(ids);
	}
	
}
