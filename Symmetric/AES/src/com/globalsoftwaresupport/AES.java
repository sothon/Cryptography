package com.globalsoftwaresupport;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AES {

	private SecretKey secretKey;
	private SecureRandom random;
	private Cipher encryptCipher;
	private Cipher decryptCipher;
	private IvParameterSpec ivParams;
	
	public AES() {
		random = new SecureRandom();
		try {
			secretKey = KeyGenerator.getInstance("AES").generateKey();
			encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] initializatonVector = new byte[encryptCipher.getBlockSize()];
			random.nextBytes(initializatonVector);
			ivParams = new IvParameterSpec(initializatonVector);
			encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);
			decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {		
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
	}
	
	public String encrypt(String plainText) {
		byte[] bytes = plainText.getBytes();
		byte[] cipherText = null;
		
		try {
			cipherText = encryptCipher.doFinal(bytes);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(cipherText);
	}
	
	public String decrypt(String cipherText) {
		
		byte[] plainText = null;
		
		try {
			plainText = decryptCipher.doFinal(Base64.getDecoder().decode(cipherText.getBytes()));
			return new String(plainText, "UTF8");
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}





