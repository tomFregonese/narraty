package com.ynov.javaformation.narraty.services;

import com.ynov.javaformation.narraty.interfaces.services.IPasswordService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class PasswordService implements IPasswordService {

    public String hashPassword(String password) {
        byte[] salt = generateSalt();
        byte[] hash = computeHash(password, salt);
        return combineSaltAndHash(salt, hash);
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        byte[] hashBytes = Base64.getDecoder().decode(hashedPassword);
        byte[] salt = extractSalt(hashBytes);
        byte[] hash = computeHash(plainPassword, salt);
        return compareHashes(hash, hashBytes);
    }

    private boolean compareHashes(byte[] computedHash, byte[] hashBytes) {
        for (int i = 0; i < 20; i++) {
            if (hashBytes[i + 16] != computedHash[i]) {
                return false;
            }
        }
        return true;
    }

    private byte[] extractSalt(byte[] hashBytes) {
        byte[] salt = new byte[16];
        System.arraycopy(hashBytes, 0, salt, 0, 16);
        return salt;
    }

    private String combineSaltAndHash(byte[] salt, byte[] hash) {
        byte[] combined = new byte[36];
        System.arraycopy(salt, 0, combined, 0, 16);
        System.arraycopy(hash, 0, combined, 16, 20);
        return Base64.getEncoder().encodeToString(combined);
    }

    private byte[] computeHash(String password, byte[] salt) {
        try {
            int iterations = 10_000;
            int keyLength = 160;

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

}
