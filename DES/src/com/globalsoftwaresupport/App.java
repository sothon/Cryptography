package com.globalsoftwaresupport;

public class App {

	public static void main(String[] args) {
		DES des = new DES();
		String text = "Cryptography is important in cryptocurrencies!";
		String cipherText = des.encrypt(text);
		System.out.println(cipherText);
		System.out.println(des.decrypt(cipherText));
	}
}