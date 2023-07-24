package com.globalsoftwaresupport;

public class OneTimePad {

	public String encrypt(String plainText, int[] key) {
		
		// we want to make the algorithm case insensitive
		plainText = plainText.toUpperCase();
		String cipherText = "";
		
		// consider all the letters in the plainText
		for(int i=0;i<plainText.length();++i) {
			// because numOfLettersInPlainText == numOfRandomNumbers
			// the formula is quite simple
			int keyIndex = key[i];
			int characterIndex = Constants.ALPHABET.indexOf(plainText.charAt(i));
			
			// encryptedLetterIndex = (characterIndex+randomShiftIndex) mod 27
			cipherText += Constants.ALPHABET.charAt(Math.floorMod(characterIndex +
					keyIndex, Constants.ALPHABET.length()));	
		}
		
		return cipherText;
	}
	
	public String decrypt(String cipherText, int[] key) {
		
		cipherText = cipherText.toUpperCase();
		String plainText = "";
		
		for(int i=0;i<cipherText.length();++i) {
			int keyIndex = key[i];
			int characterIndex = Constants.ALPHABET.indexOf(cipherText.charAt(i));
			
			plainText += Constants.ALPHABET.charAt(Math.floorMod(characterIndex -
					keyIndex, Constants.ALPHABET.length()));
		}
		
		return plainText;
	}
}


