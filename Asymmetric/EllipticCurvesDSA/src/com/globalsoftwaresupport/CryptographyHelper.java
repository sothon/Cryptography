package com.globalsoftwaresupport;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;

public class CryptographyHelper {

	// public key (x,y) and the private key (256 bit)
	public static KeyPair generateKeys() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
			// random numbers - 160 bit
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec params = new ECGenParameterSpec("prime256v1");
			keyPairGenerator.initialize(params, random);
			return keyPairGenerator.generateKeyPair();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// private key - signature generation
	// public key  verifying
	public static byte[] sign(PrivateKey privateKey, String message) {
		Signature signature;
		
		try {
			signature = Signature.getInstance("ECDSA", "BC");
			signature.initSign(privateKey);
			signature.update(message.getBytes());
			return signature.sign();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean verify(PublicKey publicKey, String message, byte[] signature) {
		
		try {
			Signature algorithm = Signature.getInstance("ECDSA", "BC");
			algorithm.initVerify(publicKey);
			algorithm.update(message.getBytes());
			return algorithm.verify(signature);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}




