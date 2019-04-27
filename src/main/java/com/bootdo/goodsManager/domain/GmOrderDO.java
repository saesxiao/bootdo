package com.bootdo.goodsManager.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 发货订单表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-20 14:04:15
 */
public class GmOrderDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//订单表id
	private Long id;
	//用户id
	private Long userId;
	//上级id
	private Long parentId;
	//订单金额
	private Double amount;
	//订单唯一编号
	private String orderCode;
	//1-待处理2-已处理3-失效
	private Integer orderStatus;
	//订单日期
	private String orderTime;
	//处理或失效日期
	private String endTime;
	//第一次下单0以后1
	private String type;
	//截图地址
	private String other;
	//收货地址
	private String remark;
	//物流编号
	private String postCode;

	/**
	 * 设置：订单表id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：订单表id
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
	 * 设置：订单金额
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * 获取：订单金额
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * 设置：订单唯一编号
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	/**
	 * 获取：订单唯一编号
	 */
	public String getOrderCode() {
		return orderCode;
	}
	/**
	 * 设置：1-待处理2-已处理3-失效
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取：1-待处理2-已处理3-失效
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置：订单日期
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	/**
	 * 获取：订单日期
	 */
	public String getOrderTime() {
		return orderTime;
	}
	/**
	 * 设置：处理或失效日期
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：处理或失效日期
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 设置：订单编号
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：订单编号
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：截图地址
	 */
	public void setOther(String other) {
		this.other = other;
	}
	/**
	 * 获取：截图地址
	 */
	public String getOther() {
		return other;
	}
	/**
	 * 设置：收货地址
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：收货地址
	 */
	public String getRemark() {
		return remark;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
}
