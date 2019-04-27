package com.bootdo.goodsManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.goodsManager.dao.GmUserStatusDao;
import com.bootdo.goodsManager.domain.GmUserStatusDO;
import com.bootdo.goodsManager.service.GmUserStatusService;



@Service
public class GmUserStatusServiceImpl implements GmUserStatusService {
	@Autowired
	private GmUserStatusDao gmUserStatusDao;
	
	@Override
	public GmUserStatusDO get(Long id){
		return gmUserStatusDao.get(id);
	}
	
	@Override
	public List<GmUserStatusDO> list(Map<String, Object> map){
		return gmUserStatusDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return gmUserStatusDao.count(map);
	}
	
	@Override
	public int save(GmUserStatusDO gmUserStatus){
		return gmUserStatusDao.save(gmUserStatus);
	}
	
	@Override
	public int update(GmUserStatusDO gmUserStatus){
		return gmUserStatusDao.update(gmUserStatus);
	}
	
	@Override
	public int remove(Long id){
		return gmUserStatusDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return gmUserStatusDao.batchRemove(ids);
	}
	
}
