package com.globalsoftwaresupport;

import java.math.BigInteger;

public class App {

	public static void main(String[] args) {
		
		String message = "This is a cryptography related message!";
		
		RSA rsa = new RSA();
		rsa.generateKeys(1024);
		
		BigInteger cipher = rsa.encryptMessage(message);
		System.out.println(cipher);
		System.out.println(rsa.decryptMessage(cipher));
	}
}