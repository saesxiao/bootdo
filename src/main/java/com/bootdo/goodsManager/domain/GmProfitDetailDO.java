package com.bootdo.goodsManager.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 分润明细表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-09 15:48:52
 */
public class GmProfitDetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//分润明细表id
	private Long id;
	//分润表id
	private Long profitId;
	//分润金额
	private Double amount;
	//发放分润上级
	private Long parentId;
	//分润状态 1已发放2未发放3失效
	private Integer status;
	//创建日期
	private String createTime;
	//发放或失效日期
	private String endTime;
	//预留
	private String other;
	//预留
	private String type;
	//预留
	private String remark;

	/**
	 * 设置：分润明细表id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：分润明细表id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：分润表id
	 */
	public void setProfitId(Long profitId) {
		this.profitId = profitId;
	}
	/**
	 * 获取：分润表id
	 */
	public Long getProfitId() {
		return profitId;
	}
	/**
	 * 设置：分润金额
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * 获取：分润金额
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * 设置：发放分润上级
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：发放分润上级
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：分润状态 1已发放2未发放3失效
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：分润状态 1已发放2未发放3失效
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：创建日期
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建日期
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：发放或失效日期
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：发放或失效日期
	 */
	public String getEndTime() {
		return endTime;
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
