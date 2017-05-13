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

import joshscode.ECB;

/**
 * @author Josh
 *
 */
public class ECBTest {

	private ECB ecb;
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
		//public ECB(boolean encrypt, byte[] plaintext, byte[] keyA)
		byte[] input = new byte[]{(byte) 0x54,(byte) 0x77,(byte) 0x6F,(byte) 0x20,
								  (byte) 0x4F,(byte) 0x6E,(byte) 0x65,(byte) 0x20,
								  (byte) 0x4E,(byte) 0x69,(byte) 0x6E,(byte) 0x65,
								  (byte) 0x20,(byte) 0x54,(byte) 0x77,(byte) 0x6F};
		
		byte[] key = new byte[] {(byte) 0x54,(byte) 0x68,(byte) 0x61,(byte) 0x74,
								 (byte) 0x73,(byte) 0x20,(byte) 0x6D,(byte) 0x79,
								 (byte) 0x20,(byte) 0x4B,(byte) 0x75,(byte) 0x6E,
								 (byte) 0x67,(byte) 0x20,(byte) 0x46,(byte) 0x75};
		ecb = new ECB(true, input, key);
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
		byte[][] expectedCipherText = new byte[][]{
			{(byte)0x29,(byte) 0x57,(byte) 0x40,(byte) 0x1A},
			{(byte)0xC3,(byte) 0x14,(byte) 0x22,(byte) 0x02},
			{(byte)0x50,(byte) 0x20,(byte) 0x99,(byte) 0xD7},
			{(byte)0x5F,(byte) 0xF6,(byte) 0xB3,(byte) 0x3A}
		};
		//Print the expected output
		String expectedOutput = "";
		for(int i = 0; i < expectedCipherText.length; i++){
			for(int j = 0; j < expectedCipherText[0].length; j++){
				expectedOutput += (String.format("%02X",  expectedCipherText[j][i] & 0xFF) + " ");
			}
		}
		ecb.encrypt();
				
		assertEquals(expectedOutput, outContent.toString());

	}

	/**
	 * Test method for {@link build.ECB#decrypt()}.
	 * @throws Exception 
	 */
	@Test
	public void testDecrypt() throws Exception {
		byte[][] expectedPlainText = new byte[][]{
			{(byte)0x54,(byte) 0x4F,(byte) 0x4E,(byte) 0x20},
			{(byte)0x77,(byte) 0x6E,(byte) 0x69,(byte) 0x54},
			{(byte)0x6F,(byte) 0x65,(byte) 0x6E,(byte) 0x77},
			{(byte)0x20,(byte) 0x20,(byte) 0x65,(byte) 0x6F}
		};
		//Set up new ECB object.
		//public ECB(boolean encrypt, byte[] plaintext, byte[] keyA)
		byte[] input = new byte[]{(byte) 0x29,(byte) 0xC3,(byte) 0x50,(byte) 0x5F,
								  (byte) 0x57,(byte) 0x14,(byte) 0x20,(byte) 0xF6,
								  (byte) 0x40,(byte) 0x22,(byte) 0x99,(byte) 0xB3,
								  (byte) 0x1A,(byte) 0x02,(byte) 0xD7,(byte) 0x3A};
		
		byte[] key = new byte[] {(byte) 0x54,(byte) 0x68,(byte) 0x61,(byte) 0x74,
								 (byte) 0x73,(byte) 0x20,(byte) 0x6D,(byte) 0x79,
								 (byte) 0x20,(byte) 0x4B,(byte) 0x75,(byte) 0x6E,
								 (byte) 0x67,(byte) 0x20,(byte) 0x46,(byte) 0x75};
		ecb = new ECB(false, input, key);
		
		//Print the expected output
		String expectedOutput = "";
		for(int i = 0; i < expectedPlainText.length; i++){
			for(int j = 0; j < expectedPlainText[0].length; j++){
				expectedOutput += (String.format("%02X",  expectedPlainText[j][i] & 0xFF) + " ");
			}
		}
		ecb.decrypt();
				
		assertEquals(expectedOutput, outContent.toString());
		
	}

}
