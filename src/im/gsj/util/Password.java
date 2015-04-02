package im.gsj.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 生成md5密码
 * @author Kevin
 */
public class Password {
	public static void main(String[] args) {
		String passwordMd5 = DigestUtils.md5Hex("?#Fzqw3ff"); 
		System.out.println(passwordMd5);
	}
}
