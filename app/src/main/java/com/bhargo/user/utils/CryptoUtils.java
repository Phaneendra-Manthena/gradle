package com.bhargo.user.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class CryptoUtils {

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String ASYMMETRIC_ENCRYPTION_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 256;

    public static byte[] encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData);
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ASYMMETRIC_ENCRYPTION_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    public static SecretKey generateSymmetricKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        return keyGenerator.generateKey();
    }

}
