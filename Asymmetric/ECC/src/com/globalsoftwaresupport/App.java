package com.globalsoftwaresupport;

import java.util.Random;

public class App {

	public static void main(String[] args) {
		
		// these are all public: the ECC and the generator point
		ECC ecc = new ECC(3, 7);
		Point generator = new Point(-2, 1);
		
		Random random = new Random();
		
		// Elliptic Curve Diffie-Hellman Algorithm
		// random number for Alice
		int a = random.nextInt(10000);
		// random number for Bob
		int b = random.nextInt(10000);
		
		// public keys with the double and add algorithm
		// these are points on the elliptic curve
		Point alicePublicKey = ecc.doubleAndAdd(a, generator);
		Point bobPublicKey = ecc.doubleAndAdd(b, generator);
		
		// they can generate the same private key they can use for symmetric encryption
		Point aliceSecretKey = ecc.doubleAndAdd(a, bobPublicKey);
		Point bobSecretKey = ecc.doubleAndAdd(b, alicePublicKey);
		
		System.out.println(aliceSecretKey);
		System.out.println(bobSecretKey);
	}
}