/**
 * Author: Joshua Crompton
 * Date: May 13, 2017
 * Class: ECBTest.java
 * Description:
 */
package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import joshscode.CFB;
/**
 * @author Josh
 *
 */
public class CFBTest {

	private CFB cfb;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//Set the output streams to compare System.out.println statements
		System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}
	
	@After
	public void cleanUpStreams(){
		System.setOut(null);
	    System.setErr(null);
	}

	/**
	 * Test method for {@link build.ECB#encrypt()}.
	 * @throws Exception 
	 */
	@Test
	public void testEncrypt() throws Exception {
		byte[] plaintext = new byte[] {
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
				(byte) 0xFE, (byte) 0xA2, (byte) 0x25, (byte) 0xA2, 
				(byte) 0x20, (byte) 0x75, (byte) 0xEB, (byte) 0x89, 
				(byte) 0x2D, (byte) 0x0B, (byte) 0xF3, (byte) 0x18, 
				(byte) 0xB2, (byte) 0x2E, (byte) 0x1D, (byte) 0xCE
		};
		
		int transmissionSize = 1;
		
		cfb = new CFB(true, plaintext, key, iv, transmissionSize);
		cfb.encrypt();
		
		byte[] expectedCipherText = new byte[]{
				(byte) 0x24, (byte) 0xbc, (byte) 0x99, (byte) 0xdc, 
				(byte) 0x38, (byte) 0x12, (byte) 0x10, (byte) 0x27, 
				(byte) 0x54, (byte) 0x0f, (byte) 0xcf, (byte) 0xfc, 
				(byte) 0xf7, (byte) 0xd6, (byte) 0x15, (byte) 0xfc,
				(byte) 0x39, (byte) 0x7f, (byte) 0xdf, (byte) 0x60, 
				(byte) 0xae, (byte) 0xc6, (byte) 0x47, (byte) 0x01, 
				(byte) 0x4e, (byte) 0x4e, (byte) 0x39, (byte) 0x38, 
				(byte) 0xb0, (byte) 0xf9, (byte) 0x16, (byte) 0x70

		};
				
		assertArrayEquals(expectedCipherText, cfb.getState());

	}

	/**
	 * Test method for {@link build.ECB#decrypt()}.
	 * @throws Exception 
	 */
	@Test
	public void testDecrypt() throws Exception {
		byte[] cipherText = new byte[]{
				(byte) 0x24, (byte) 0xbc, (byte) 0x99, (byte) 0xdc, 
				(byte) 0x38, (byte) 0x12, (byte) 0x10, (byte) 0x27, 
				(byte) 0x54, (byte) 0x0f, (byte) 0xcf, (byte) 0xfc, 
				(byte) 0xf7, (byte) 0xd6, (byte) 0x15, (byte) 0xfc,
				(byte) 0x39, (byte) 0x7f, (byte) 0xdf, (byte) 0x60, 
				(byte) 0xae, (byte) 0xc6, (byte) 0x47, (byte) 0x01, 
				(byte) 0x4e, (byte) 0x4e, (byte) 0x39, (byte) 0x38, 
				(byte) 0xb0, (byte) 0xf9, (byte) 0x16, (byte) 0x70
		};
		
		byte[] key = new byte[] {
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF, 
				(byte) 0x01, (byte) 0x35, (byte) 0x78, (byte) 0x91, 
				(byte) 0xAB, (byte) 0xCD, (byte) 0x00, (byte) 0xE4, 
				(byte) 0x67, (byte) 0xF0, (byte) 0x12, (byte) 0xCF	
		};
		
		byte[] iv = new byte[] {
				(byte) 0xFE, (byte) 0xA2, (byte) 0x25, (byte) 0xA2, 
				(byte) 0x20, (byte) 0x75, (byte) 0xEB, (byte) 0x89, 
				(byte) 0x2D, (byte) 0x0B, (byte) 0xF3, (byte) 0x18, 
				(byte) 0xB2, (byte) 0x2E, (byte) 0x1D, (byte) 0xCE
		};
		
		int transmissionSize = 1;
		
		cfb = new CFB(false, cipherText, key, iv, transmissionSize);
		cfb.decrypt();
		
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
		
		assertArrayEquals(expectedPlainText, cfb.getState());
		
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
					(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF};
		
		byte[] key = new byte[] {
				(byte) 0x00, (byte) 0xE4, (byte) 0x35, (byte) 0xFF, 
				(byte) 0x01, (byte) 0x35, (byte) 0x78, (byte) 0x91, 
				(byte) 0xAB, (byte) 0xCD, (byte) 0x00, (byte) 0xE4, 
				(byte) 0x67, (byte) 0xF0, (byte) 0x12, (byte) 0xCF};
		
		byte[] iv = new byte[] {
				(byte) 0xFE, (byte) 0xA2, (byte) 0x25, (byte) 0xA2, 
				(byte) 0x20, (byte) 0x75, (byte) 0xEB, (byte) 0x89, 
				(byte) 0x2D, (byte) 0x0B, (byte) 0xF3, (byte) 0x18, 
				(byte) 0xB2, (byte) 0x2E, (byte) 0x1D, (byte) 0xCE
		};
		
		int transmissionSize = 1;
		
		CFB encrypter = new CFB(true, plainText, key, iv, transmissionSize);
		encrypter.encrypt();
		byte[] cipherText = encrypter.getState();
		
		//Create a new ECB to decrypt
		CFB decrypter = new CFB(false, cipherText, key, iv, transmissionSize);
		decrypter.decrypt();
		
		byte[] decryptedPlainText = decrypter.getState();
		
		assertArrayEquals(plainText, decryptedPlainText);
		
	}

}
