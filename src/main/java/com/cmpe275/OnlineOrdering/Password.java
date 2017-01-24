package com.cmpe275.OnlineOrdering;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

/**
 * Class for creating the password hash and comparing if the password is correct or not
 * @author Somya
 *
 */
public class Password {
	private static final int iter = 20 * 1000;
	private static final int salt_length = 32;
	private static final int desired_key_ength = 256;

	/**
	 * Get the password hash
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public String getPasswordHash(String password) throws Exception {
		byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(salt_length);
		return Base64.encodeBase64String(salt) + "$" + calculateHash(password, salt);
	}

	/**
	 * Check if the password entered is same or not
	 * @param password
	 * @param existing_password
	 * @return
	 * @throws Exception
	 */
	public boolean checkPassword(String password, String existing_password) throws Exception {
		String[] salt_password = existing_password.split("\\$");
		if (salt_password.length != 2) {
			throw new IllegalStateException("The stored password have the form 'salt$hash'");
		}
		String inputHash = calculateHash(password, Base64.decodeBase64(salt_password[0]));
		return inputHash.equals(salt_password[1]);
	}

	/**
	 * It will calculate the hash.
	 * @param password
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	private static String calculateHash(String password, byte[] salt) throws Exception {
		if (password == null || password.length() == 0)
			throw new IllegalArgumentException("Empty passwords are not supported.");
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = factory.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iter, desired_key_ength));
		return Base64.encodeBase64String(key.getEncoded());
	}
}