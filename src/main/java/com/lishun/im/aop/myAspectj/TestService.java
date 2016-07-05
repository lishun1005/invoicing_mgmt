package com.lishun.im.aop.myAspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TestService {
	@Before("execution (* com.lishun.im.service.imp.ImStockManageServiceImpl.queryListImImStock(..))")
	public void test(){
		System.out.println("-----------------ddd");
	}
}
