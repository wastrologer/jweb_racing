package com.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

	public static String md5(String s) {
		return DigestUtils.md5Hex(s.getBytes());
    }

}
