package ru.letmerent.core.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.letmerent.core.services.DecryptService;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Slf4j
@Service
public class DecryptPasswordImpl implements DecryptService {
    @Value("${spring.mail.secret}")
    private String secret;
    
    @Override
    public String decryptPassword(String encryptedPassword) {
        try {
            int iterationCount = 30;
            int keyLength = 128;
            byte[] salt = "12345678".getBytes();
            SecretKeySpec key = createSecretKey(secret.toCharArray(),
                salt, iterationCount, keyLength);
            
            return decrypt(encryptedPassword, key);
        } catch (GeneralSecurityException e) {
            log.error("Can't decrypt password {}", e.getMessage());
        }
        
        return null;
    }
    
    private SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }
    
    private String decrypt(String string, SecretKeySpec key) throws GeneralSecurityException {
        String iv = string.split(":")[0];
        String property = string.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        
        return new String(pbeCipher.doFinal(base64Decode(property)), StandardCharsets.UTF_8);
    }
    
    private byte[] base64Decode(String property) {
        return Base64.getDecoder().decode(property);
    }
}
