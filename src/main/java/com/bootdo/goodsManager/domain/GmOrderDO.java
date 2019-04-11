package com.bootdo.goodsManager.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 发货订单表
 * 
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-04 16:01:15
 */
public class GmOrderDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//订单表id
	private Long id;
	//用户id
	private Long userId;
	//上级id
	private Long parentId;
	//商品表id
	private Integer goodsId;
	//商品数量
	private Integer goodsNum;
	//1-待处理2-已处理3-失效
	private Integer orderStatus;
	//订单日期
	private String orderTime;
	//处理或失效日期
	private String endTime;
	//订单编号
	private String type;
	//截图地址
	private String other;
	//收货地址
	private String remark;

	//商品名称
	private String goodsName;

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
	 * 设置：商品表id
	 */
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	/**
	 * 获取：商品表id
	 */
	public Integer getGoodsId() {
		return goodsId;
	}
	/**
	 * 设置：商品数量
	 */
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	/**
	 * 获取：商品数量
	 */
	public Integer getGoodsNum() {
		return goodsNum;
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
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：预留
	 */
	public String getRemark() {
		return remark;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
