/**
 * Author: Joshua Crompton
 * Date: May 13, 2017
 * Class: CBCTest.java
 * Description:
 */
package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import joshscode.CBC;

/**
 * @author Josh
 *
 */
public class CBCTest {

	private CBC cbc;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link build.CBC#encrypt()}.
	 * @throws Exception 
	 */
	@Test
	public void testEncrypt() throws Exception {
	
		byte[] plainText = new byte[] {
				(byte) 0x5A, (byte) 0x67, (byte) 0xF0, (byte) 0x12, 
				(byte) 0xCF, (byte) 0x98, (byte) 0xAB, (byte) 0xCD, 
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF, 
				(byte) 0x01, (byte) 0x35, (byte) 0x78, (byte) 0x91, 
				(byte) 0xFA, (byte) 0xC2, (byte) 0xCF, (byte) 0x98, 
				(byte) 0xAB, (byte) 0xCD, (byte) 0x00, (byte) 0xE4, 
				(byte) 0x35, (byte) 0xFF, (byte) 0x01, (byte) 0x35, 
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF
		};
		
		byte[] key = new byte[] {
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF, 
				(byte) 0x01, (byte) 0x35, (byte) 0x78, (byte) 0x91, 
				(byte) 0xAB, (byte) 0xCD, (byte) 0x00, (byte) 0xE4, 
				(byte) 0x67, (byte) 0xF0, (byte) 0x12, (byte) 0xCF
		};
		
		byte[] iv = new byte[] {
				(byte) 0xfe, (byte) 0xa2, (byte) 0x25, (byte) 0xa2, 
				(byte) 0x20, (byte) 0x75, (byte) 0xeb, (byte) 0x89,
				(byte) 0x2d, (byte) 0x0b, (byte) 0xf3, (byte) 0x18, 
				(byte) 0xb2, (byte) 0x2e, (byte) 0x1d, (byte) 0xce
		};
		
		//Create CBC encrypter
		cbc = new CBC(true, iv, plainText, key);
		//Encrypt
		cbc.encrypt();
		
		//Expected cipher text from online AES tool
		byte[] expectedCipherText = new byte[] {
				(byte) 0x80, (byte) 0xce, (byte) 0xf0, (byte) 0x31, 
				(byte) 0xce, (byte) 0x09, (byte) 0x8a, (byte) 0x2f, 
				(byte) 0x19, (byte) 0x7c, (byte) 0xb7, (byte) 0xb6, 
				(byte) 0x2a, (byte) 0x01, (byte) 0xbc, (byte) 0x33, 
				(byte) 0xa1, (byte) 0x41, (byte) 0xd4, (byte) 0xd6, 
				(byte) 0x85, (byte) 0xbd, (byte) 0x81, (byte) 0x7f, 
				(byte) 0xd3, (byte) 0x1f, (byte) 0x0c, (byte) 0xd8, 
				(byte) 0x37, (byte) 0x08, (byte) 0x25, (byte) 0xb3
		};
		
		assertArrayEquals(expectedCipherText, cbc.getState());
		
		
	}

	/**
	 * Test method for {@link build.CBC#decrypt()}.
	 * @throws Exception 
	 */
	@Test
	public void testDecrypt() throws Exception {
		//Expected cipher text from online AES tool
		byte[] cipherText = new byte[] {
				(byte) 0x80, (byte) 0xce, (byte) 0xf0, (byte) 0x31, 
				(byte) 0xce, (byte) 0x09, (byte) 0x8a, (byte) 0x2f, 
				(byte) 0x19, (byte) 0x7c, (byte) 0xb7, (byte) 0xb6, 
				(byte) 0x2a, (byte) 0x01, (byte) 0xbc, (byte) 0x33, 
				(byte) 0xa1, (byte) 0x41, (byte) 0xd4, (byte) 0xd6, 
				(byte) 0x85, (byte) 0xbd, (byte) 0x81, (byte) 0x7f, 
				(byte) 0xd3, (byte) 0x1f, (byte) 0x0c, (byte) 0xd8, 
				(byte) 0x37, (byte) 0x08, (byte) 0x25, (byte) 0xb3
		};
				
		byte[] key = new byte[] {
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF, 
				(byte) 0x01, (byte) 0x35, (byte) 0x78, (byte) 0x91, 
				(byte) 0xAB, (byte) 0xCD, (byte) 0x00, (byte) 0xE4, 
				(byte) 0x67, (byte) 0xF0, (byte) 0x12, (byte) 0xCF
		};
		
		byte[] iv = new byte[] {
				(byte) 0xfe, (byte) 0xa2, (byte) 0x25, (byte) 0xa2, 
				(byte) 0x20, (byte) 0x75, (byte) 0xeb, (byte) 0x89,
				(byte) 0x2d, (byte) 0x0b, (byte) 0xf3, (byte) 0x18, 
				(byte) 0xb2, (byte) 0x2e, (byte) 0x1d, (byte) 0xce
		};
		
		//Create CBC encrypter
		cbc = new CBC(false, iv, cipherText, key);
		//Encrypt
		cbc.decrypt();
		
		byte[] expectedPlainText = new byte[] {
				(byte) 0x5A, (byte) 0x67, (byte) 0xF0, (byte) 0x12, 
				(byte) 0xCF, (byte) 0x98, (byte) 0xAB, (byte) 0xCD, 
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF, 
				(byte) 0x01, (byte) 0x35, (byte) 0x78, (byte) 0x91, 
				(byte) 0xFA, (byte) 0xC2, (byte) 0xCF, (byte) 0x98, 
				(byte) 0xAB, (byte) 0xCD, (byte) 0x00, (byte) 0xE4, 
				(byte) 0x35, (byte) 0xFF, (byte) 0x01, (byte) 0x35, 
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF
		};
		
		assertArrayEquals(expectedPlainText, cbc.getState());
	}
	
	@Test
	public void testEncryptionAndDecryoptionWork() throws Exception{
		byte[] plainText = new byte[] {
				(byte) 0x5A, (byte) 0x67, (byte) 0xF0, (byte) 0x12, 
				(byte) 0xCF, (byte) 0x98, (byte) 0xAB, (byte) 0xCD, 
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF, 
				(byte) 0x01, (byte) 0x35, (byte) 0x78, (byte) 0x91, 
				(byte) 0xFA, (byte) 0xC2, (byte) 0xCF, (byte) 0x98, 
				(byte) 0xAB, (byte) 0xCD, (byte) 0x00, (byte) 0xE4, 
				(byte) 0x35, (byte) 0xFF, (byte) 0x01, (byte) 0x35, 
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF
		};
		
		byte[] key = new byte[] {
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF, 
				(byte) 0x01, (byte) 0x35, (byte) 0x78, (byte) 0x91, 
				(byte) 0xAB, (byte) 0xCD, (byte) 0x00, (byte) 0xE4, 
				(byte) 0x67, (byte) 0xF0, (byte) 0x12, (byte) 0xCF
		};
		
		byte[] iv = new byte[] {
				(byte) 0xfe, (byte) 0xa2, (byte) 0x25, (byte) 0xa2, 
				(byte) 0x20, (byte) 0x75, (byte) 0xeb, (byte) 0x89,
				(byte) 0x2d, (byte) 0x0b, (byte) 0xf3, (byte) 0x18, 
				(byte) 0xb2, (byte) 0x2e, (byte) 0x1d, (byte) 0xce
		};
		
		//Create an ECB object to encrypt the message
		CBC encrypter = new CBC(true, iv, plainText, key);
		encrypter.encrypt();
		
		byte[] cipherText = encrypter.getState();
		
		//Check the cipher text is as expected.
		byte[] expectedCipherText = new byte[] {
				(byte) 0x80, (byte) 0xce, (byte) 0xf0, (byte) 0x31, 
				(byte) 0xce, (byte) 0x09, (byte) 0x8a, (byte) 0x2f, 
				(byte) 0x19, (byte) 0x7c, (byte) 0xb7, (byte) 0xb6, 
				(byte) 0x2a, (byte) 0x01, (byte) 0xbc, (byte) 0x33, 
				(byte) 0xa1, (byte) 0x41, (byte) 0xd4, (byte) 0xd6, 
				(byte) 0x85, (byte) 0xbd, (byte) 0x81, (byte) 0x7f, 
				(byte) 0xd3, (byte) 0x1f, (byte) 0x0c, (byte) 0xd8, 
				(byte) 0x37, (byte) 0x08, (byte) 0x25, (byte) 0xb3
		};
		
		assertArrayEquals(expectedCipherText, cipherText);
		
		//Create a new ECB to decrypt
		CBC decrypter = new CBC(false, iv, cipherText, key);
		decrypter.decrypt();
		
		byte[] decryptedPlainText = decrypter.getState();
		
		assertArrayEquals(plainText, decryptedPlainText);
		
	}


}
