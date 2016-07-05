package com.lishun.im.resultBean;

public class ResultBean<T> {
	/**
	 * 状态码
	 */
	public ResultCode resultCode;
	/**
	 * 消息
	 */
	public String message;
	/**
	 * 类型
	 */
	public T bean;
	
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
	public T getBean() {
		return bean;
	}
	public void setBean(T bean) {
		this.bean = bean;
	}
	
}
