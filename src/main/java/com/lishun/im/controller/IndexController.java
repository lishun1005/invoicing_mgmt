package com.lishun.im.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class IndexController {
	

	/**
	 * 
	 * Description：后台管理 登录成功后，跳转到后台管理中心首页
	 * @param user
	 * @param model
	 * @return       
	 *
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model  model,HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		return "index";
	}
	
	/**
	 * 
	 * Description：***
	 * @param model
	 * @return       
	 *
	 */
	@RequestMapping(value = "unauthorized", method = RequestMethod.GET)
	public String unauthorized(Model  model){
		System.out.println("没有权限");
		model.addAttribute("msg", "你没有权限执行该操作，请联系管理员");
		return "unauthorized";
	}
	
	/**
	 * 
	 * Description：***
	 * @param model
	 * @return       
	 *
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(Model  model){		
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			subject.logout();
		}
		return "redirect:login";
	}
	
	/**
	 * 
	 * Description：后台管理 跳转到用户登录页面
	 * @param model
	 * @return       
	 *
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(Model model,String iframeUrl){
		model.addAttribute("iframeUrl", iframeUrl);
		return "login";
	}
	
	/**
	 * 
	 * Description：后台管理 使用输入的用户名，密码登录系统
	 * @param model
	 * @return       
	 *
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String loginSystem(Model model, String username, String password,String iframeUrl){
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		String error = null;
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			error=e.getMessage();
			model.addAttribute("errorMessage", e.getMessage());
		}
		if (error==null) {	
			if(StringUtils.isNotEmpty(iframeUrl)){
				
				if(iframeUrl.indexOf("index")!=-1){//判断是否是首页刷新
					return "redirect:index";
				}
				return "redirect:index?iframeUrl="+iframeUrl;
			}
			return "redirect:index";
		} else {
			return "redirect:login";
		}
	}
	
	@RequestMapping(value = "sysIndex", method = RequestMethod.GET)
	public String sysIndex(){
		return "sysIndex";
	}

	
	
}
