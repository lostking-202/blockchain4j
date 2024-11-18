package com.example.demo.account;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.graalvm.collections.Pair;
import org.junit.Assert;
import org.junit.Test;

public class AccountGenerator {

  public Pair<String,String> genAsymmetricEncrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    var keyPairGen=KeyPairGenerator.getInstance("RSA");
    keyPairGen.initialize(512,SecureRandom.getInstanceStrong());
    var keyPair=keyPairGen.generateKeyPair();
    var publicKey=(RSAPublicKey)keyPair.getPublic();
    var privateKey=(RSAPrivateKey)keyPair.getPrivate();
    return Pair.create(Base64.getEncoder().encodeToString(publicKey.getEncoded()),Base64.getEncoder().encodeToString(privateKey.getEncoded()));

  }

  @Test
  public void test() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {

    var keyPair=genAsymmetricEncrypt();

    System.out.println(keyPair.getLeft());
    System.out.println(keyPair.getRight());
    var message="hello world";
    var cipher= Cipher.getInstance("RSA");

    cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(keyPair.getLeft()))));
    var encrypted=cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

    cipher.init(Cipher.DECRYPT_MODE,KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(keyPair.getRight()))));
    var decrypted=cipher.doFinal(encrypted);
    Assert.assertEquals(message,new String(decrypted,StandardCharsets.UTF_8));
  }

}
