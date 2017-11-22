package com.zcc.po;

import org.msgpack.annotation.Message;

/**
 * 用户信息实体
 * @author zcc
 *
 * @date 2017年11月17日
 */
@Message
public class UserInfo {

	private int userId;
	private String userName;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
