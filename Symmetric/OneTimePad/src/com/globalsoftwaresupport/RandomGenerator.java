package com.globalsoftwaresupport;

import java.util.Random;

public class RandomGenerator {

	private Random random;
	
	public RandomGenerator() {
		this.random = new Random();
	}
	
	// let's generate n pseudo-random (!!!) numbers
	public int[] generate(int n) {
		
		int[] randomSequence = new int[n];
		
		// the random numbers will be within the range [0,ALPHABET_SIZE-1]
		for(int i=0;i<n;++i)
			randomSequence[i] = random.nextInt(Constants.ALPHABET.length());
			
		return randomSequence;
	}
}