package com.globalsoftwaresupport;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.MGF1ParameterSpec;
import java.util.HexFormat;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class App {

	// we do not use RSA for encryption and decryption
	// reason: RSA is dealing with extremely large numbers (exponent)
	// AES - sessionKey (private key for AES)
	// RSA (or ECC) to encrypt this session key
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		Security.addProvider(new BouncyCastleProvider());
		
		String message = "This is just a simple message";

		// generate 2048 bits long RSA keys
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		
		KeyPair keyPair = generator.genKeyPair();
		// decrypt the session key (private key for AES)
		PrivateKey privateKey = keyPair.getPrivate();
		// encrypt the session key (private key for AES)
		PublicKey publicKey = keyPair.getPublic();
		
		// CLINT SIDE
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey sessionKey = keyGenerator.generateKey();
		
		System.out.println(HexFormat.of().formatHex(sessionKey.getEncoded()));
		
		// initialization vector (IV)
		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[16];
		random.nextBytes(iv);
		
		// AES encryption
		AES aesEncrypt = new AES(sessionKey, iv);
		String cipherText = aesEncrypt.encrypt(message);
		System.out.println("Cipher text: " + cipherText);
		
		// use the RSA public key for encrypting the session key
		Cipher encryptionCipher = 
				Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
		OAEPParameterSpec spec = new OAEPParameterSpec("SHA-256", "MGF1", 
				MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
		encryptionCipher.init(Cipher.ENCRYPT_MODE, publicKey, spec);
		
		// encrypted session key
		byte[] encryptedSessionKey = encryptionCipher.doFinal(sessionKey.getEncoded());
		
		System.out.println(HexFormat.of().formatHex(encryptedSessionKey));
		
		// we send from client to server:
		// cipherText, encrypted session key, RSA public key, iv
		// SERVER SIDE
		Cipher decryptionCipher = Cipher
				.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
		OAEPParameterSpec spec2 = new OAEPParameterSpec("SHA-256", "MGF1", 
				MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
		decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey, spec2);
		
		byte[] decryptedKey = decryptionCipher.doFinal(encryptedSessionKey);
		System.out.println(HexFormat.of().formatHex(decryptedKey));
		
		// AES with this session key to decrypt the cipher text
		SecretKey decryptedSessionKey = new SecretKeySpec(decryptedKey, 0, 
				decryptedKey.length, "AES");
		AES decryptAES = new AES(decryptedSessionKey, iv);
		
		System.out.println("Decrypted message: " + decryptAES.decrypt(cipherText));
	}
}



