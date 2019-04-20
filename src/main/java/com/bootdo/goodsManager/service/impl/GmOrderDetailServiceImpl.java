package com.bootdo.goodsManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.goodsManager.dao.GmOrderDetailDao;
import com.bootdo.goodsManager.domain.GmOrderDetailDO;
import com.bootdo.goodsManager.service.GmOrderDetailService;



@Service
public class GmOrderDetailServiceImpl implements GmOrderDetailService {
	@Autowired
	private GmOrderDetailDao gmOrderDetailDao;
	
	@Override
	public GmOrderDetailDO get(Long id){
		return gmOrderDetailDao.get(id);
	}
	
	@Override
	public List<GmOrderDetailDO> list(Map<String, Object> map){
		return gmOrderDetailDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return gmOrderDetailDao.count(map);
	}
	
	@Override
	public int save(GmOrderDetailDO gmOrderDetail){
		return gmOrderDetailDao.save(gmOrderDetail);
	}
	
	@Override
	public int update(GmOrderDetailDO gmOrderDetail){
		return gmOrderDetailDao.update(gmOrderDetail);
	}
	
	@Override
	public int remove(Long id){
		return gmOrderDetailDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return gmOrderDetailDao.batchRemove(ids);
	}
	
}
