package com.enc;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;

import static org.assertj.core.api.Assertions.*;

public class EncryptionTests {


    @Test
    public void testLoadCipger() throws NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = getRsaCipher();
        assertThat(cipher).isNotNull();
    }

    @Test
    public void testLoadKey() throws NoSuchAlgorithmException {
        /*KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec()
        kf.generatePrivate();*/
    }

    @Test
    public void testInitCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
//        Cipher cipher = getRsaCipher();
//        cipher.init(Cipher.ENCRYPT_MODE,);
    }

    private static Cipher getRsaCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.getInstance("RSA");
    }
}
