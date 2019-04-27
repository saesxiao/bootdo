package com.bootdo.goodsManager.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 记录用户状态表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-27 09:11:10
 */
public class GmUserStatusDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//用户状态表id
	private Long id;
	//用户id
	private Long userId;
	//晋升状态
	private String promotionStatus;
	//晋升通知状态
	private String promotionNotice;
	//分润状态
	private String profitStatus;
	//分润通知状态
	private String profitNotice;
	//预留
	private String other;
	//预留
	private String type;
	//预留
	private String remark;

	/**
	 * 设置：用户状态表id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：用户状态表id
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
	 * 设置：晋升状态
	 */
	public void setPromotionStatus(String promotionStatus) {
		this.promotionStatus = promotionStatus;
	}
	/**
	 * 获取：晋升状态
	 */
	public String getPromotionStatus() {
		return promotionStatus;
	}
	/**
	 * 设置：晋升通知状态
	 */
	public void setPromotionNotice(String promotionNotice) {
		this.promotionNotice = promotionNotice;
	}
	/**
	 * 获取：晋升通知状态
	 */
	public String getPromotionNotice() {
		return promotionNotice;
	}
	/**
	 * 设置：分润状态
	 */
	public void setProfitStatus(String profitStatus) {
		this.profitStatus = profitStatus;
	}
	/**
	 * 获取：分润状态
	 */
	public String getProfitStatus() {
		return profitStatus;
	}
	/**
	 * 设置：分润通知状态
	 */
	public void setProfitNotice(String profitNotice) {
		this.profitNotice = profitNotice;
	}
	/**
	 * 获取：分润通知状态
	 */
	public String getProfitNotice() {
		return profitNotice;
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
