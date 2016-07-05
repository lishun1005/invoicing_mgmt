package com.lishun.im.resultBean;

public class ResultMessage {
	/**
	 * 状态码
	 */
	public ResultCode resultCode;
	/**
	 * 消息
	 */
	public String message;
	
	public ResultCode getResultCode() {
		return resultCode;
	}
	public void setResultCode(ResultCode resultCode) {
		this.resultCode = resultCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
