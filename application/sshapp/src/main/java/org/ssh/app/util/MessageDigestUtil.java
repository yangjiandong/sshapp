/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssh.app.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Ideally, we can use commons-codec for the following task, but
 * since glassfish has its own repacked 1.2 commons-codec, but I want the SHA512 in commons-codec-1.4.jar
 * and I did not found the way to workaround.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class MessageDigestUtil {

    private static final Log log = LogFactory.getLog(MessageDigestUtil.class);

    /**
     * MD5 hash function to be "cryptographically secure", the result is 32 HEX characters.
     * <ol>
     * <li>Given a hash, it is computationally infeasible to find an input that produces that hash</li>
     * <li>Given an input, it is computationally infeasible to find another input that produces the same hash</li>
     * </ol>
     *
     * @param plainText
     * @param salt
     * @return the MD% of the input text
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(byte[] plainText, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(salt);
        String md5 = new BigInteger(1, messageDigest.digest(plainText)).toString(16);
        if (md5.length() < 32) {
            md5 = "0" + md5;
        }

        messageDigest.reset();

        if (log.isDebugEnabled()) {
            log.debug(new String(plainText, "UTF8") + "[salt=" + new String(salt, "UTF8") + "] 's MD5: " + md5);
        }

        return md5;
    }

    /**
     * SHA-512 hash function to be "cryptographically secure", the result is 128 HEX characters
     * <ol>
     * <li>Given a hash, it is computationally infeasible to find an input that produces that hash</li>
     * <li>Given an input, it is computationally infeasible to find another input that produces the same hash</li>
     * </ol>
     *
     * @param plainText
     * @param salt
     * @return the SHA-512 of the input text
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha512(byte[] plainText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        String sha512 = new BigInteger(1, messageDigest.digest(plainText)).toString(16);
        if (sha512.length() < 128) {
            sha512 = "0" + sha512;
        }

        messageDigest.reset();

        if (log.isDebugEnabled()) {
            log.debug(new String(plainText, "UTF8") + "'s SHA512: " + sha512);
        }

        return sha512;
    }
}
