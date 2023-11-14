package com.fabric.utils;

import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.sdk.Enrollment;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @description 用户工具类用于读取证书和私钥信息到java对象中
 */
public class UserUtils {

   private static class CAEnrollment implements Enrollment{
        private PrivateKey key;
        private String ecert;
        public CAEnrollment(PrivateKey key,String ecert) {
            this.key = key;
            this.ecert = ecert;
        }
        @Override
        public PrivateKey getKey() {
            return key;
        }

        @Override
        public String getCert() {
            return ecert;
        }
    }

    /**
     * @description 根据证书目录和私钥目录读取到enrollment里面。
     * @param keyFolderPath 私钥的目录
     * @param keyFileName   私钥的文件名
     * @param certFolderPath 证书的目录
     * @param certFileName   证书的文件名
     * @return enrollment 带有用户信息的对象
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CryptoException
     * @throws InvalidKeySpecException
     */
    public static Enrollment getEnrollment(String keyFolderPath, String keyFileName, String certFolderPath, String certFileName)
            throws Exception {
        PrivateKey key = null;
        String certificate = null;
        InputStream isKey = null;
        BufferedReader brKey = null;

        try {

            isKey = new FileInputStream(keyFolderPath + File.separator + keyFileName);
            brKey = new BufferedReader(new InputStreamReader(isKey));
            StringBuilder keyBuilder = new StringBuilder();

            for (String line = brKey.readLine(); line != null; line = brKey.readLine()) {
                if (line.indexOf("PRIVATE") == -1) {
                    keyBuilder.append(line);
                }
            }

            certificate = new String(Files.readAllBytes(Paths.get(certFolderPath, certFileName)));

            byte[] encoded = DatatypeConverter.parseBase64Binary(keyBuilder.toString());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("ECDSA");
            key = kf.generatePrivate(keySpec);
        } finally {
            isKey.close();
            brKey.close();
        }
       return new CAEnrollment(key, certificate);
    }
}
