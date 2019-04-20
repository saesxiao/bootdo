package com.bootdo.goodsManager.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 订单明细表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-20 13:48:30
 */
public class GmOrderDetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//订单明细表id
	private Long id;
	//订单表id
	private Long orderId;
	//商品id
	private Integer goodsId;
	//商品数量
	private Integer goodsNum;
	//预留
	private String type;
	//预留
	private String other;
	//预留
	private String remark;

	// 商品名称
	private String goodsName;
	// 商品单价
	private String goodsPrice;

	/**
	 * 设置：订单明细表id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：订单明细表id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：订单表id
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单表id
	 */
	public Long getOrderId() {
		return orderId;
	}
	/**
	 * 设置：商品id
	 */
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	/**
	 * 获取：商品id
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

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	@Override
	public String toString() {
		return "GmOrderDetailDO{" +
				"id=" + id +
				", orderId=" + orderId +
				", goodsId=" + goodsId +
				", goodsNum=" + goodsNum +
				", type='" + type + '\'' +
				", other='" + other + '\'' +
				", remark='" + remark + '\'' +
				", goodsName='" + goodsName + '\'' +
				", goodsPrice='" + goodsPrice + '\'' +
				'}';
	}
}
