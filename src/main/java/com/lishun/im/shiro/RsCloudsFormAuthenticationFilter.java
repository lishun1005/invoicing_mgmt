package com.lishun.im.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class RsCloudsFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest rs = null;
		
		if (request instanceof HttpServletRequest) {
			rs = (HttpServletRequest) request;
		}
		//获取配置文件的loginUrl
		String loginUrl=getLoginUrl();
		if (rs != null) {
			//获取完整的url
			String iframeUrl=getFullURL(rs);
			Integer idx;
			if((idx=iframeUrl.indexOf("iframeUrl"))!=-1){
				//如果已经存在iframeUrl这个参数，就截取最后一个iframeUrl参数值，最后一个iframeUrl参数值是最新请求的url值
				iframeUrl=iframeUrl.substring(idx+"iframeUrl".length()+1);
			}
			String url="";
			//当直接访问项目名url(http://localhost:8084/rscloudmart-manage/);就不设置iframeUrl
			if(iframeUrl.lastIndexOf("/")!=iframeUrl.length()-1){
				url=loginUrl + "?iframeUrl="+iframeUrl;
				//设置带有参数的loginUrl
				setLoginUrl(url);
			}
		}
		Boolean result= super.onAccessDenied(request, response);
		//要从新设置loginUrl,保证每次获取到的都是配置文件的loginUrl
		setLoginUrl(loginUrl);
		return result;
	}
	/**
	 * description:获取完整的Url(带get参数的的url)
	 * @param request
	 * @return
	 */
	public String getFullURL(HttpServletRequest request) {

		StringBuffer url = request.getRequestURL();
		if (request.getQueryString() != null) {
			url.append("?");
			url.append(request.getQueryString());
		}
		return url.toString();
	}
}
