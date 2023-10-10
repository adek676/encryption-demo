package com.key;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.Signature;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;


class KeysTest {

    @Test
    public void testGenerateECKey() {
        Keys keys = new Keys();
        KeyPair kp = keys.ecP256KeyPair();
        assertThat(kp).isNotNull();

    }

    @Test
    public void signDataWithECDSA() throws Exception{
        Keys keys = new Keys();
        KeyPair kp = keys.ecP256KeyPair();
        String input = "test input";
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        Signature signatureECDSA = Signature.getInstance("SHA384withECDSA", "BC");
        signatureECDSA.initSign(kp.getPrivate());
        signatureECDSA.update(inputBytes);
        byte[] signedInput = signatureECDSA.sign();
        assertThat(signedInput).is(new Condition<>(predicate(inputBytes), ""));
        System.out.println("Signed input: " + new String(signedInput));
    }

    private Predicate<byte[]> predicate(byte[] external) {
        return bytes -> external.length != bytes.length;
    }
}