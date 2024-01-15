package org.hca.middleware.EventHandler.util;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class CryptUtil {

    private static final Logger LOGGER = LogManager.getLogger(CryptUtil.class);

    @Value("${key.file.path}")
    private String keyFilePath;

    @Value("${iv.file.path}")
    private String ivFilePath;

    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;
    private Cipher cipher;

    @PostConstruct
    public void init(){
        initCipher(this.keyFilePath, this.ivFilePath);
    }

    public void initCipher(String keyFilePath, String ivFilePath){
        try{
            byte[] keyBytes = Files.readAllBytes(Paths.get(keyFilePath));
            this.secretKey = new SecretKeySpec(keyBytes, "AES");

            byte[] ivBytes = Files.readAllBytes(Paths.get(ivFilePath));
            this.ivParameterSpec = new IvParameterSpec(ivBytes);

            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch(IOException io){
            LOGGER.error("Error while reading files " ,io);
        } catch(NoSuchPaddingException | NoSuchAlgorithmException ne) {
            LOGGER.error("Error while getting cipher instance ", ne);
        } catch (Exception e){
            LOGGER.error("General exception ", e);
        }
    }

    public String encrypt(String plainText){
        String result = "";
        try{
            this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.ivParameterSpec);

            byte[] plaintextBytes = plainText.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = cipher.doFinal(plaintextBytes);
            result = Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e){
            LOGGER.error("Error while encrypting data ", e);
        }
        return result;
    }

    public String decrypt(String cipherText){
        String result = "";
        try{
            byte[] encryptedBytes = Base64.getDecoder().decode(cipherText);
            this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.ivParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            result = new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e){
            LOGGER.error("Error while decrypting data ", e);
        }
        return result;
    }
}
