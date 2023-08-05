package com.globalsoftwaresupport;

import java.math.BigInteger;

public class App {

	public static void main(String[] args) {
		
		// it should be a huge prime number
		BigInteger n = new BigInteger(Integer.toString(37));
		
		// g is the primitive root of n
		BigInteger g = new BigInteger(Integer.toString(13));
		
		DiffieHellman algorithm = new DiffieHellman();
		algorithm.generatePrivateKeys(n, g);				
	}
}