package com.bootdo.goodsManager.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 商品对应用户表
 * 
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-02 15:44:34
 */
public class GmGoodsUserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//商品表id
	private Integer id;
	//对应用户表id
	private Long userId;
	//商品状态(0-库存 1-已售)
	private String status;
	//商品id
	private String type;
	//预留
	private String other;
	//预留
	private String remark;
	//进货日期
	private String inTime;
	//出货日期
	private String outTime;
	//批次
	private Integer batch;
	//商品识别码
	private String goodsCode;

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
	 * 设置：对应用户表id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：对应用户表id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：商品状态(0-库存 1-已售)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：商品状态(0-库存 1-已售)
	 */
	public String getStatus() {
		return status;
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
	/**
	 * 设置：进货日期
	 */
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	/**
	 * 获取：进货日期
	 */
	public String getInTime() {
		return inTime;
	}
	/**
	 * 设置：出货日期
	 */
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	/**
	 * 获取：出货日期
	 */
	public String getOutTime() {
		return outTime;
	}
	/**
	 * 设置：批次
	 */
	public void setBatch(Integer batch) {
		this.batch = batch;
	}
	/**
	 * 获取：批次
	 */
	public Integer getBatch() {
		return batch;
	}
	/**
	 * 设置：商品识别码
	 */
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	/**
	 * 获取：商品识别码
	 */
	public String getGoodsCode() {
		return goodsCode;
	}

	@Override
	public String toString() {
		return "GmGoodsUserDO{" +
				"id=" + id +
				", userId=" + userId +
				", status='" + status + '\'' +
				", type='" + type + '\'' +
				", other='" + other + '\'' +
				", remark='" + remark + '\'' +
				", inTime='" + inTime + '\'' +
				", outTime='" + outTime + '\'' +
				", batch=" + batch +
				", goodsCode='" + goodsCode + '\'' +
				'}';
	}
}
