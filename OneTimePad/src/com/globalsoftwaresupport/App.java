package com.globalsoftwaresupport;

public class App {

	public static void main(String[] args) {
		
		String plainText = "Cryptography is important in Bitcoin and other cryptocurrencies";
		
		RandomGenerator random = new RandomGenerator();
		int[] key = random.generate(plainText.length());
		
		OneTimePad algorithm = new OneTimePad();
		String cipher = algorithm.encrypt(plainText, key);
		
		System.out.println(cipher);
		System.out.println(algorithm.decrypt(cipher, key));
	}
}