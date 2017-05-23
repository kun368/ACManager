package com.zzkun.util.geetest;

/**
 * GeetestWeb配置文件
 * 
 *
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	private static final String captcha_id = "258269bb69779a51dc96047af8f9ab51";
	private static final String private_key = "d67e592a0f8672bde7fac3daf658bcf5";

	public static final String getCaptcha_id() {
		return captcha_id;
	}

	public static final String getPrivate_key() {
		return private_key;
	}

}
