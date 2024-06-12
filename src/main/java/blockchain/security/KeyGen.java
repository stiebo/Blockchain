package blockchain.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyGen {
    private KeyGen() { }

    public static KeyPair generateKeyPair(int keyLength) {
        KeyPairGenerator keyGen;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGen.initialize(keyLength);
        return keyGen.generateKeyPair();
    }
}
