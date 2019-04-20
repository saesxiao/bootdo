package com.bootdo.goodsManager.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 商品信息表
 * 
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-02 15:44:34
 */
public class GmGoodsInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//商品表id
	private Integer id;
	//商品名称
	private String goodsName;
	//商品单价
	private String goodsPrice;
	//商品条码
	private String barCode;
	//状态(1可售0停售)
	private Integer statu;
	//商品描述
	private String describe;
	//添加时间
	private String createTime;
	//预留
	private String other;
	//预留
	private String remark;

	/**
	 * 设置：商品表id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：商品表id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：商品名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 获取：商品名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 设置：商品单价
	 */
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	/**
	 * 获取：商品单价
	 */
	public String getGoodsPrice() {
		return goodsPrice;
	}
	/**
	 * 设置：商品条码
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	/**
	 * 获取：商品条码
	 */
	public String getBarCode() {
		return barCode;
	}
	/**
	 * 设置：状态(1可售0停售)
	 */
	public void setStatu(Integer statu) {
		this.statu = statu;
	}
	/**
	 * 获取：状态(1可售0停售)
	 */
	public Integer getStatu() {
		return statu;
	}
	/**
	 * 设置：商品描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * 获取：商品描述
	 */
	public String getDescribe() {
		return describe;
	}
	/**
	 * 设置：添加时间
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：添加时间
	 */
	public String getCreateTime() {
		return createTime;
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

	@Override
	public String toString() {
		return "GmGoodsInfoDO{" +
				"id=" + id +
				", goodsName='" + goodsName + '\'' +
				", goodsPrice=" + goodsPrice +
				", barCode='" + barCode + '\'' +
				", statu=" + statu +
				", describe='" + describe + '\'' +
				", createTime='" + createTime + '\'' +
				", other='" + other + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}
}
