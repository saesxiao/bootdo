package com.bootdo.goodsManager.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 分润表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-09 15:48:52
 */
public class GmProfitDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//分润表id
	private Long id;
	//用户id
	private Long userId;
	//上级id
	private Long parentId;
	//分润总金额
	private Double totalAmount;
	//等级
	private String level;
	//预留
	private String other;
	//预留
	private String type;
	//预留
	private String remark;

	/**
	 * 设置：分润表id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：分润表id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：上级id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级id
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：分润总金额
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * 获取：分润总金额
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * 设置：等级
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * 获取：等级
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * 设置：预留
	 */
	public void setOther(String other) {
		this.other = other;
	}
	/**
	 * 获取：预留
	 */
	public String getOther() {
		return other;
	}
	/**
	 * 设置：预留
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：预留
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：预留
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：预留
	 */
	public String getRemark() {
		return remark;
	}
}
