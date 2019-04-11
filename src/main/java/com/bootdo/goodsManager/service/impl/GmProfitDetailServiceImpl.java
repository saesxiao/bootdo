package com.bootdo.goodsManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.goodsManager.dao.GmProfitDetailDao;
import com.bootdo.goodsManager.domain.GmProfitDetailDO;
import com.bootdo.goodsManager.service.GmProfitDetailService;



@Service
public class GmProfitDetailServiceImpl implements GmProfitDetailService {
	@Autowired
	private GmProfitDetailDao gmProfitDetailDao;
	
	@Override
	public GmProfitDetailDO get(Long id){
		return gmProfitDetailDao.get(id);
	}
	
	@Override
	public List<GmProfitDetailDO> list(Map<String, Object> map){
		return gmProfitDetailDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return gmProfitDetailDao.count(map);
	}
	
	@Override
	public int save(GmProfitDetailDO gmProfitDetail){
		return gmProfitDetailDao.save(gmProfitDetail);
	}
	
	@Override
	public int update(GmProfitDetailDO gmProfitDetail){
		return gmProfitDetailDao.update(gmProfitDetail);
	}
	
	@Override
	public int remove(Long id){
		return gmProfitDetailDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return gmProfitDetailDao.batchRemove(ids);
	}
	
}
