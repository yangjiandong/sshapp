package org.springside.modules.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * <p>Title: RSAUtil</p>
 * <p>Description: Utility class that helps encrypt and decrypt strings using RSA algorithm</p>
 * @author Aviran Mordo http://aviran.mordos.com
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public final class RSAUtil {

    private static final Log log = LogFactory.getLog(RSAUtil.class);
    private static final String ALGORITHM = "RSA";
    private static final int keyLength = 1024;

    private RSAUtil() {
    }

    /**
     * Generate key which contains a pair of private and public key using 1024 bytes
     * @return key pair
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(keyLength);
        KeyPair keyPair = keyGen.generateKeyPair();
        return keyPair;
    }

    /**
     * Encrypt a text using public key.
     * @param text The original unencrypted text
     * @param key The public key
     * @return Encrypted text
     * @throws java.lang.Exception
     */
    public static byte[] encrypt(byte[] text, PublicKey key) throws Exception {
        byte[] cipherText = null;
        try {
            //
            // get an RSA cipher object and print the provider
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            if (log.isDebugEnabled()) {
                log.debug("\nProvider is: " + cipher.getProvider().getInfo());
                log.debug("\nStart encryption with public key");
            }

            // encrypt the plaintext using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text);
        } catch (Exception e) {
            log.error(e, e);
            throw e;
        }
        return cipherText;
    }

    /**
     * Encrypt a text using public key. The result is enctypted BASE64 encoded text
     * @param text The original unencrypted text
     * @param key The public key
     * @return Encrypted text encoded as BASE64
     * @throws java.lang.Exception
     */
    public static String encrypt(String text, PublicKey key) throws Exception {
        String encryptedText;
        try {
            byte[] cipherText = encrypt(text.getBytes("UTF8"), key);
            encryptedText = encodeBASE64(cipherText);
            if (log.isDebugEnabled()) {
                log.debug("Enctypted text is: " + encryptedText);
            }
        } catch (Exception e) {
            log.error(e, e);
            throw e;
        }
        return encryptedText;
    }

    /**
     * Decrypt text using private key
     * @param text The encrypted text
     * @param key The private key
     * @return The unencrypted text
     * @throws java.lang.Exception
     */
    public static byte[] decrypt(byte[] text, PrivateKey key) throws Exception {
        byte[] dectyptedText = null;
        try {
            // decrypt the text using the private key
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            if (log.isDebugEnabled()) {
                log.debug("\nProvider is: " + cipher.getProvider().getInfo());
                log.debug("Start decryption");
            }
            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(text);
        } catch (Exception e) {
            log.error(e, e);
            throw e;
        }
        return dectyptedText;

    }

    /**
     * Decrypt BASE64 encoded text using private key
     * @param text The encrypted text, encoded as BASE64
     * @param key The private key
     * @return The unencrypted text encoded as UTF8
     * @throws java.lang.Exception
     */
    public static String decrypt(String text, PrivateKey key) throws Exception {
        String result;
        try {
            // decrypt the text using the private key
            byte[] dectyptedText = decrypt(decodeBASE64(text), key);
            result = new String(dectyptedText, "UTF8");
            if (log.isDebugEnabled()) {
                log.debug("Decrypted text is: " + result);
            }
        } catch (Exception e) {
            log.error(e, e);
            throw e;
        }
        return result;

    }

    /**
     * Convert a Key to string encoded as BASE64
     * @param key The key (private or public)
     * @return A string representation of the key
     */
    public static String getKeyAsString(Key key) {
        // Get the bytes of the key
        byte[] keyBytes = key.getEncoded();
        // Convert key to BASE64 encoded string
        return new String(Base64.encodeBase64(keyBytes), Charset.forName("UTF-8"));
    }

    /**
     * Generates Private Key from BASE64 encoded string
     * @param key BASE64 encoded string which represents the key
     * @return The PrivateKey
     * @throws java.lang.Exception
     */
    public static PrivateKey getPrivateKeyFromString(String key) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key.getBytes(Charset.forName("UTF-8"))));
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        return privateKey;
    }

    /**
     * Generates Public Key from BASE64 encoded string
     * @param key BASE64 encoded string which represents the key
     * @return The PublicKey
     * @throws java.lang.Exception
     */
    public static PublicKey getPublicKeyFromString(String key) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key.getBytes(Charset.forName("UTF-8"))));
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }

    /**
     * Encode bytes array to BASE64 string
     * @param bytes
     * @return Encoded string
     */
    private static String encodeBASE64(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes), Charset.forName("UTF-8"));
    }

    /**
     * Decode BASE64 encoded string to bytes array
     * @param text The string
     * @return Bytes array
     * @throws IOException
     */
    private static byte[] decodeBASE64(String text) throws IOException {
        return Base64.decodeBase64(text.getBytes(Charset.forName("UTF-8")));
    }

    /**
     * Encrypt file using 1024 RSA encryption
     *
     * @param srcFileName Source file name
     * @param destFileName Destination file name
     * @param key The key. For encryption this is the Private Key and for decryption this is the public key
     * @param cipherMode Cipher Mode
     * @throws Exception
     */
    public static void encryptFile(String srcFileName, String destFileName, PublicKey key) throws Exception {
        encryptOrDecryptFile(srcFileName, destFileName, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * Decrypt file using 1024 RSA encryption
     *
     * @param srcFileName Source file name
     * @param destFileName Destination file name
     * @param key The key. For encryption this is the Private Key and for decryption this is the public key
     * @param cipherMode Cipher Mode
     * @throws Exception
     */
    public static void decryptFile(String srcFileName, String destFileName, PrivateKey key) throws Exception {
        encryptOrDecryptFile(srcFileName, destFileName, key, Cipher.DECRYPT_MODE);
    }

    /**
     * Encrypt and Decrypt files using 1024 RSA encryption
     *
     * @param srcFileName Source file name
     * @param destFileName Destination file name
     * @param key The key. For encryption this is the Private Key and for decryption this is the public key
     * @param cipherMode Cipher Mode
     * @throws Exception
     */
    private static void encryptOrDecryptFile(String srcFileName, String destFileName, Key key, int cipherMode) throws Exception {
        OutputStream outputWriter = null;
        InputStream inputReader = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            if (log.isDebugEnabled()) {
                log.debug("\nProvider is: " + cipher.getProvider().getInfo());
            }

            //RSA encryption data size limitations are slightly less than the key modulus size,
            //depending on the actual padding scheme used (e.g. with 1024 bit (128 byte) RSA key,
            //the size limit is 117 bytes for PKCS#1 v 1.5 padding. (http://www.jensign.com/JavaScience/dotnet/RSAEncrypt/)
            byte[] buf = cipherMode == Cipher.ENCRYPT_MODE ? new byte[100] : new byte[128];
            int bufl;
            // init the Cipher object for Encryption...
            cipher.init(cipherMode, key);

            // start FileIO
            outputWriter = new FileOutputStream(destFileName);
            inputReader = new FileInputStream(srcFileName);
            while ((bufl = inputReader.read(buf)) != -1) {
                byte[] encText = null;
                if (cipherMode == Cipher.ENCRYPT_MODE) {
                    encText = encrypt(copyBytes(buf, bufl), (PublicKey) key);
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("buf = " + new String(buf));
                    }
                    encText = decrypt(copyBytes(buf, bufl), (PrivateKey) key);
                }
                outputWriter.write(encText);
                if (log.isDebugEnabled()) {
                    log.debug("encText = " + new String(encText));
                }
            }
            outputWriter.flush();

        } catch (Exception e) {
            log.error(e, e);
            throw e;
        } finally {
            try {
                if (outputWriter != null) {
                    outputWriter.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (Exception e) {
                // do nothing...
            } // end of inner try, catch (Exception)...
        }
    }

    private static byte[] copyBytes(byte[] arr, int length) {
        byte[] newArr = null;
        if (arr.length == length) {
            newArr = arr;
        } else {
            newArr = new byte[length];
            for (int i = 0; i < length; i++) {
                newArr[i] = (byte) arr[i];
            }
        }
        return newArr;
    }
}
