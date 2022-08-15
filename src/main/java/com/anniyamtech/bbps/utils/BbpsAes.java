package com.anniyamtech.bbps.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mgs.bbps.security.SecurityUtils;

@Component
public class BbpsAes {
	@Value("${encryption.key}")
	String key;
	SecurityUtils utils = new SecurityUtils();

	public String encryption(String data) {
		String encDataGcm;
		try {
			encDataGcm = utils.encryptAesGcm(data, key);
			return encDataGcm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decryption(String encDataGcm) {

		try {
			String decDataGcm = utils.decryptAesGcm(encDataGcm, key);
			return decDataGcm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
