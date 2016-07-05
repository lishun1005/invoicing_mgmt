package com.lishun.im.shiro.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.lishun.im.bean.SysUser;

/**
 * <p>
 * User: xiangsx
 * <p>
 * Date: 2015-03-17
 * <p>
 * Version: 1.0
 */
@Service("passwordHelper")
public class PasswordHelper {

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";
	private int hashIterations = 2;

	public void setRandomNumberGenerator(
			RandomNumberGenerator randomNumberGenerator) {
		this.randomNumberGenerator = randomNumberGenerator;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	public void encryptPassword(SysUser user) {

		user.setSalt(randomNumberGenerator.nextBytes().toHex());

		String newPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getUsername() + user.getSalt()),
				hashIterations).toHex();

		user.setPassword(newPassword);
	}

	public String encryptPassword(String password, String salt) {
		String newPassword = new SimpleHash(algorithmName, password,
				ByteSource.Util.bytes(salt), hashIterations).toHex();
		return newPassword;
	}

	public static void main(String[] args) {
		SysUser user = new SysUser();
		user.setUsername("admin");
		user.setPassword("wm@123");
		new PasswordHelper().encryptPassword(user);
		System.out.println("username:"+user.getUsername());
		System.out.println("passwd:" +user.getPassword());
		System.out.println("aslt:"+user.getSalt());
	}
}
