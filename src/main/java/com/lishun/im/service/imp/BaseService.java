package com.lishun.im.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.lishun.im.resultBean.ResultBean;
import com.lishun.im.resultBean.ResultMessage;

public class BaseService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public <T> ResultBean<T> getResultBean(){
		return new ResultBean<T>();
	}
	
	public  ResultMessage getResultMessage(){
		return new ResultMessage();
	}
}
