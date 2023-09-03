package com.globalsoftwaresupport;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v1CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CertificateBuilder {

	// version-1 for self-signed certificates provides a basic way of associating
	// identity and validity time to a public key
	// first name = certificate issuer
	// second name = certificate subject (in this case it is the same !!!)
	public static X509Certificate makeV1Certificate(PrivateKey caSignerKey, PublicKey caPublicKey)
			throws GeneralSecurityException, IOException, OperatorCreationException {

		X509v1CertificateBuilder v1CertBldr = new X509v1CertificateBuilder(new X500Name("CN=Issuer CA"),
				BigInteger.valueOf(System.currentTimeMillis()), new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + (long) 3.156e+10), new X500Name("CN=Issuer CA"), (SubjectPublicKeyInfo) caPublicKey);

		JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder("SHA256withECDSA");

		// the converter is needed to convert a certificate object from the BC local one
		// X509CertificateHolder to the JCA one X509Certificate
		return new JcaX509CertificateConverter().getCertificate(v1CertBldr.build(signerBuilder.build(caSignerKey)));
	}
}
