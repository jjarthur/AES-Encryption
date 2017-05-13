/**
 * Author: Joshua Crompton
 * Date: May 13, 2017
 * Class: StateTest.java
 * Description:
 */
package test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import joshscode.State;

/**
 * @author Josh
 *
 */
public class StateTest {

	/*
	 //Set up matrix to check against 
		byte[][] shiftedMatrix = new byte[][]{
			{(byte) 0x00, (byte) 0x04, (byte) 0x08, (byte) 0x0C},
			{(byte) 0x01, (byte) 0x05, (byte) 0x09, (byte) 0x0D},
			{(byte) 0x02, (byte) 0x06, (byte) 0x0A, (byte) 0x0E},
			{(byte) 0x03, (byte) 0x07, (byte) 0x0B, (byte) 0x0F}
		}; 
	 */
	
	
	private State state;
	private byte[] input;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		input = new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
							(byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07,
							(byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
							(byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
		state = new State(input);
		
	}

	/**
	 * Test method for {@link build.State#inverseShiftRows()}.
	 */
	@Test
	public void testInverseShiftRows() {
		//Set up matrix to check against 
		byte[][] shiftedMatrix = new byte[][]{
			{(byte) 0x00, (byte) 0x04, (byte) 0x08, (byte) 0x0C},
			{(byte) 0x0D, (byte) 0x01, (byte) 0x05, (byte) 0x09},
			{(byte) 0x0A, (byte) 0x0E, (byte) 0x02, (byte) 0x06},
			{(byte) 0x07, (byte) 0x0B, (byte) 0x0F, (byte) 0x03}
		};
		
		state.inverseShiftRows();
		
		//Check each row of the matrix
		for(int i = 0; i < shiftedMatrix.length; i++){
			Assert.assertArrayEquals(shiftedMatrix[i], state.getState()[i]);
		}
		
	}

	/**
	 * Test method for {@link build.State#shiftRow(byte[], int)}.
	 */
	@Test
	public void testShiftRow() {
		//Set up matrix to check against 
		byte[][] shiftedMatrix = new byte[][]{
			{(byte) 0x00, (byte) 0x04, (byte) 0x08, (byte) 0x0C},
			{(byte) 0x05, (byte) 0x09, (byte) 0x0D, (byte) 0x01},
			{(byte) 0x0A, (byte) 0x0E, (byte) 0x02, (byte) 0x06},
			{(byte) 0x0F, (byte) 0x03, (byte) 0x07, (byte) 0x0B}
		};
		
		state.shiftRows();
		
		//Check each row of the matrix
		for(int i = 0; i < shiftedMatrix.length; i++){
			Assert.assertArrayEquals(shiftedMatrix[i], state.getState()[i]);
		}
	}

	@Test
	public void testSubByte(){
		//Set up matrix to check against 
		byte[][] subbedMatrix = new byte[][]{
			{(byte) 0x63, (byte) 0xF2, (byte) 0x30, (byte) 0xFE},
			{(byte) 0x7C, (byte) 0x6B, (byte) 0x01, (byte) 0xD7},
			{(byte) 0x77, (byte) 0x6F, (byte) 0x67, (byte) 0xAB},
			{(byte) 0x7B, (byte) 0xC5, (byte) 0x2B, (byte) 0x76}
		};
		
		state.subBytes();
		
		//Check each row of the matrix
		for(int i = 0; i < subbedMatrix.length; i++){
			Assert.assertArrayEquals(subbedMatrix[i], state.getState()[i]);
		}
	}
	
	@Test
	public void testInverseSubByte(){
		//Set up matrix to check against 
		byte[][] subbedMatrix = new byte[][]{
			{(byte) 0x52, (byte) 0x30, (byte) 0xBF, (byte) 0x81},
			{(byte) 0x09, (byte) 0x36, (byte) 0x40, (byte) 0xF3},
			{(byte) 0x6A, (byte) 0xA5, (byte) 0xA3, (byte) 0xD7},
			{(byte) 0xD5, (byte) 0x38, (byte) 0x9E, (byte) 0xFB}
		};
		
		state.inverseSubBytes();
		
		//Check each row of the matrix
		for(int i = 0; i < subbedMatrix.length; i++){
			Assert.assertArrayEquals(subbedMatrix[i], state.getState()[i]);
		}
	}
	
	@Test
	public void testMixColumns() throws Exception{
		//Create new State using input found online
		byte[] newInput = new byte[] {(byte) 0x63, (byte) 0x2F, (byte) 0xAF, (byte) 0xA2,
										(byte) 0xEB, (byte) 0x93, (byte) 0xC7, (byte) 0x20,
										(byte) 0x9F, (byte) 0x92, (byte) 0xAB, (byte) 0xCB,
										(byte) 0xA0, (byte) 0xC0, (byte) 0x30, (byte) 0x2B}; 
		
		state = new State(newInput);
		
		//Set up matrix to check against 
		byte[][] mixedMatrix = new byte[][]{
			{(byte) 0xBA, (byte) 0x84, (byte) 0xE8, (byte) 0x1B},
			{(byte) 0x75, (byte) 0xA4, (byte) 0x8D, (byte) 0x40},
			{(byte) 0xF4, (byte) 0x8D, (byte) 0x06, (byte) 0x7D},
			{(byte) 0x7A, (byte) 0x32, (byte) 0x0E, (byte) 0x5D}
		};
		
		state.mixColumns(false);
		
		//Check each row of the matrix
		for(int i = 0; i < mixedMatrix.length; i++){
			Assert.assertArrayEquals(mixedMatrix[i], state.getState()[i]);
		}
	}
	
	@Test
	public void testInverseMixColumns(){
		
	}
	
	@Test
	public void testAddRoundKey() throws Exception{
		//Create new State using input found online
		byte[] newInput = new byte[] {(byte) 0x54, (byte) 0x77, (byte) 0x6f, (byte) 0x20,
									  (byte) 0x4F, (byte) 0x6E, (byte) 0x65, (byte) 0x20,
								  	  (byte) 0x4E, (byte) 0x69, (byte) 0x6E, (byte) 0x65,
								  	  (byte) 0x20, (byte) 0x54, (byte) 0x77, (byte) 0x6F}; 
		
		state = new State(newInput);
		
		byte[][] key = new byte[][] {
			 {(byte) 0x54, (byte) 0x73, (byte) 0x20, (byte) 0x67},
			 {(byte) 0x68, (byte) 0x20, (byte) 0x4B, (byte) 0x20},
			 {(byte) 0x61, (byte) 0x6D, (byte) 0x75, (byte) 0x46},
			 {(byte) 0x74, (byte) 0x79, (byte) 0x6E, (byte) 0x75}
		};
		
		//Set up matrix to check against 
		byte[][] stateAfterAddingROundKey = new byte[][]{
			 {(byte) 0x00 ,(byte) 0x3C ,(byte) 0x6E ,(byte) 0x47},
			 {(byte) 0x1F ,(byte) 0x4E ,(byte) 0x22 ,(byte) 0x74},
			 {(byte) 0x0E ,(byte) 0x08 ,(byte) 0x1B ,(byte) 0x31},
			 {(byte) 0x54 ,(byte) 0x59 ,(byte) 0x0B ,(byte) 0x1A}
		};
		
		state.addRoundKey(key);
		
		//Check each row of the matrix
		for(int i = 0; i < stateAfterAddingROundKey.length; i++){
			Assert.assertArrayEquals(stateAfterAddingROundKey[i], state.getState()[i]);
		}
	}
	
}
