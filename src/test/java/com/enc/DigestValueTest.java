package com.enc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class DigestValueTest {

    @Test
    public void testGenerateDigest() {
        String encoded =
                "nihUFQg4mDhLgecvhIcKb9Gz8VRTOlw+adiZOBBXgK4JodEe5aFfCqm8WcRIT8GLLXSk8PsUP4//SsKqUBQkpotcAqQAhtz2v9kCWdoUDnAOtFZkd/CnsZ1sge0ndha40wWDV+nOWyJxkYgicvB8POYtSmldLLepPGMz+J7/Uws=";

        byte[] byteTab = Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8));
        printByteTab(byteTab, " ");
    }

    @Test
    public void printLoadedFileBytes() throws IOException, NoSuchAlgorithmException {
        InputStream in = getClass().getResourceAsStream("/inp.xml");
        byte[] inpFileBytes = in.readAllBytes();
        printByteTabAsHex(inpFileBytes, true, 16);
        MessageDigest sha1Digest = MessageDigest.getInstance("SHA1");
        byte[] sha1Bytes = sha1Digest.digest(inpFileBytes);
//        printByteTab(sha1Bytes, " ");
        System.out.println("sha1 bytes");
        printByteTabAsHex(sha1Bytes, true, 0);
        System.out.println("base64 encoded: " + Base64.getEncoder().encodeToString(sha1Bytes));
    }

    @Test
    public void printLoadedFileBytes2() throws IOException, NoSuchAlgorithmException {
        InputStream in = getClass().getResourceAsStream("/inp2.xml");
        byte[] inpFileBytes = in.readAllBytes();
        printByteTabAsHex(inpFileBytes, true, 16);
        MessageDigest sha1Digest = MessageDigest.getInstance("SHA1");
        byte[] sha1Bytes = sha1Digest.digest(inpFileBytes);
//        printByteTab(sha1Bytes, " ");
        System.out.println("sha1 bytes");
        printByteTabAsHex(sha1Bytes, true, 0);
        System.out.println("base64 encoded: " + Base64.getEncoder().encodeToString(sha1Bytes));
    }

    @Test
    public void printSignedInfoBytes() throws Exception {
        InputStream in = getClass().getResourceAsStream("/inp-sig-info.xml");
        byte[] inpSigInfo = in.readAllBytes();
        printByteTabAsHex(inpSigInfo, false, 40);
        byte[] sha1Bytes = sha1Digest(inpSigInfo, true);
        System.out.println("base64 encoded: " + Base64.getEncoder().encodeToString(sha1Bytes));
    }

    @Test
    public void digestHexText() throws NoSuchAlgorithmException {
        String input = "3c456e76656c6f706520786d6c6e733d22687474703a2f2f6578616d706c652e6f72672f656e76656c6f7065223e0a20203c426f64793e0a202020204f6cc3a1206d756e646f0a20203c2f426f64793e0a0a3c2f456e76656c6f70653e".toUpperCase();
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        byte[] sha1Digested = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        printByteTab(sha1Digested, " ");
        printByteTabAsHex(sha1Digested, false, 0);
    }

    @Test
    public void generateKeyPairAndSaveToFile2() throws Exception {
//        KeyPair kp = KeyPairGenerator.getInstance("RSA").generateKeyPair();
//        KeyFactory.getInstance("rsa").generatePublic(new RSAPrivateKeySpec())
//        System.out.println(Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded()));
//        System.out.println(Base64.getEncoder().encodeToString(kp.getPublic().getEncoded()));
    }

    private byte[] sha1Digest(byte[] inputToDigest, boolean printAsHex) throws Exception{
        byte[] resp = MessageDigest.getInstance("SHA1").digest(inputToDigest);
        if (printAsHex) {
            System.out.println("sha1 bytes");
            printByteTabAsHex(resp, false, 0);
        }
        return resp;
    }

    private void printByteTab(byte[] input, String whitespace) {
        for (byte b : input) {
            int temp = b;
            if (b < 0) {
                temp = b + 256;
            }
            System.out.print(temp + whitespace);
        }
        System.out.println();
    }

    private void printByteTabAsHex(byte[] input, boolean addwhitespace, int breakLine) {
        int counter = 0;
        for (byte b : input) {
            int temp = b;
            if (b < 0) {
                temp = b + 256;
            }
            String hexByte = Integer.toHexString(temp);
            if (hexByte.length() == 1) {
                hexByte = "0".concat(hexByte);
            }
            if (addwhitespace) {
                System.out.print(hexByte + " ");
            }else {
                System.out.print(hexByte);
            }
            if (breakLine != 0) {
                if (counter++ == breakLine - 1) {
                    counter = 0;
                    System.out.print("\n");
                }
            }
        }
        System.out.println();
    }
}
