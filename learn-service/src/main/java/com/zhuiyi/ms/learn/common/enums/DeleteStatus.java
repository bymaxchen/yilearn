/**
 * 文件名：DeleteType.java
 *
 * 版本信息：
 * 日期：2018年8月17日 下午3:52:21
 * Copyright @Zhuiyi Inc 2018
 * 版权所有
 *
 */
package com.zhuiyi.ms.learn.common.enums;
/**
 * 删除状态枚举
 *
 * @author crazyhu@wezhuiyi.com
 * @createTime 2018-08-23 10:55:46
 */
public enum DeleteStatus {
	/**
	 * 未删除
	 */
	UN_DELETE(0, "未删除"),
	DELETED(1, "已删除")
	;
	private Integer value;

	private String desc;

	DeleteStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public Integer value() {
		return value;
	}
	
	public String desc() {
		return desc;
	}
}