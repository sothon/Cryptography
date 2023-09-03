package com.globalsoftwaresupport;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyPair;
import java.security.Security;
import java.util.Base64;

public class App {

    public static void main(String[] args) {

        // define the provider (BC)
        Security.addProvider(new BouncyCastleProvider());

//		KeyPair keys = CryptographyHelper.generateKeys();

//		try {
//			System.out.println(CertificateBuilder.makeV1Certificate(keys.getPrivate(), keys.getPublic()));
//		} catch (OperatorCreationException | GeneralSecurityException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		Print Key
        System.out.println("\n ===Print Keys===");
        KeyPair keys = CryptographyHelper.generateKeys();
        System.out.println(keys.getPublic());
        System.out.println(keys.getPrivate());
        System.out.println(keys.getPublic().getEncoded());
        System.out.println(keys.getPrivate().getEncoded());
        System.out.println(Base64.getEncoder().encodeToString(keys.getPublic().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(keys.getPrivate().getEncoded()));

//		Print Sign
        System.out.println("\n ===Print Sign===");
        System.out.println(Base64.getEncoder().encodeToString(CryptographyHelper.sign(keys.getPrivate(), "This is a message")));

        //    Print Verify Sign
        System.out.println("\n ===Print Verify Sign===");
        KeyPair keys1 = CryptographyHelper.generateKeys();
        KeyPair keys2 = CryptographyHelper.generateKeys();

        String m = "This is a message";
        byte[] signature = CryptographyHelper.sign(keys1.getPrivate(), m);
        System.out.println(CryptographyHelper.verify(keys1.getPublic(), m, signature));
        System.out.println(CryptographyHelper.verify(keys2.getPublic(), m, signature));
    }
}
