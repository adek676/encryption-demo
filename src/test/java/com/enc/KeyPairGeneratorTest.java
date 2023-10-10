package com.enc;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyPairGeneratorTest {

//    @Test
    public void generateKeyPairAndSaveToFile() throws Exception {
        KeyPair kp = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        FileOutputStream fileOutputStream = new FileOutputStream("/home/adecode/conf/genkeys/keypub");
        fileOutputStream.write(kp.getPrivate().getEncoded());
        FileOutputStream fileOutputStreamPrivate = new FileOutputStream("/home/adecode/conf/genkeys/keypri");
        fileOutputStreamPrivate.write(kp.getPrivate().getEncoded());
    }



    public void testLoadKey() throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] pubKeyBytes = getClass().getResourceAsStream("/keys/keypub").readAllBytes();
        System.out.println();
        EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(pubKeyBytes);

    }
}
