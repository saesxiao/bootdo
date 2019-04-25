package com.bootdo.goodsManager.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户晋升表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-25 08:55:54
 */
public class GmPromotionDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//晋升表id
	private Long id;
	//用户id
	private Long userId;
	//之前等级id
	private Long oldDept;
	//晋升后等级id
	private Long newDept;
	//晋升时间
	private String promotionTime;
	//通知状态(0未通知1已通知)
	private Integer noticeStatus;
	//预留
	private String type;
	//预留
	private String other;
	//预留
	private String remark;

	/**
	 * 设置：晋升表id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：晋升表id
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
	 * 设置：之前等级id
	 */
	public void setOldDept(Long oldDept) {
		this.oldDept = oldDept;
	}
	/**
	 * 获取：之前等级id
	 */
	public Long getOldDept() {
		return oldDept;
	}
	/**
	 * 设置：晋升后等级id
	 */
	public void setNewDept(Long newDept) {
		this.newDept = newDept;
	}
	/**
	 * 获取：晋升后等级id
	 */
	public Long getNewDept() {
		return newDept;
	}
	/**
	 * 设置：晋升时间
	 */
	public void setPromotionTime(String promotionTime) {
		this.promotionTime = promotionTime;
	}
	/**
	 * 获取：晋升时间
	 */
	public String getPromotionTime() {
		return promotionTime;
	}
	/**
	 * 设置：通知状态(0未通知1已通知)
	 */
	public void setNoticeStatus(Integer noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	/**
	 * 获取：通知状态(0未通知1已通知)
	 */
	public Integer getNoticeStatus() {
		return noticeStatus;
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
}
