/**
 * Author: Joshua Crompton
 * Date: May 12, 2017
 * Class: Key.java
 * Description:
 */
package joshscode;

import java.util.concurrent.SynchronousQueue;

/**
 * @author Josh
 *
 */
public class Key {

	 private static byte[] rcon = new byte[]{
			 (byte)  0x8d, (byte)  0x01, (byte)  0x02, (byte)  0x04, (byte)  0x08, (byte)  0x10, (byte)  0x20, (byte)  0x40, (byte)  0x80, (byte)  0x1b, 
			 (byte)  0x36, (byte)  0x6c, (byte)  0xd8, (byte)  0xab, (byte)  0x4d, (byte)  0x9a, (byte)  0x2f, (byte)  0x5e, (byte)  0xbc, (byte)  0x63, 
			 (byte)  0xc6, (byte)  0x97, (byte)  0x35, (byte)  0x6a, (byte)  0xd4, (byte)  0xb3, (byte)  0x7d, (byte)  0xfa, (byte)  0xef, (byte)  0xc5, 
			 (byte)  0x91, (byte)  0x39, (byte)  0x72, (byte)  0xe4, (byte)  0xd3, (byte)  0xbd, (byte)  0x61, (byte)  0xc2, (byte)  0x9f, (byte)  0x25, 
			 (byte)  0x4a, (byte)  0x94, (byte)  0x33, (byte)  0x66, (byte)  0xcc, (byte)  0x83, (byte)  0x1d, (byte)  0x3a, (byte)  0x74, (byte)  0xe8, 
			 (byte)  0xcb, (byte)  0x8d, (byte)  0x01, (byte)  0x02, (byte)  0x04, (byte)  0x08, (byte)  0x10, (byte)  0x20, (byte)  0x40, (byte)  0x80, 
			 (byte)  0x1b, (byte)  0x36, (byte)  0x6c, (byte)  0xd8, (byte)  0xab, (byte)  0x4d, (byte)  0x9a, (byte)  0x2f, (byte)  0x5e, (byte)  0xbc, 
			 (byte)  0x63, (byte)  0xc6, (byte)  0x97, (byte)  0x35, (byte)  0x6a, (byte)  0xd4, (byte)  0xb3, (byte)  0x7d, (byte)  0xfa, (byte)  0xef, 
			 (byte)  0xc5, (byte)  0x91, (byte)  0x39, (byte)  0x72, (byte)  0xe4, (byte)  0xd3, (byte)  0xbd, (byte)  0x61, (byte)  0xc2, (byte)  0x9f, 
			 (byte)  0x25, (byte)  0x4a, (byte)  0x94, (byte)  0x33, (byte)  0x66, (byte)  0xcc, (byte)  0x83, (byte)  0x1d, (byte)  0x3a, (byte)  0x74, 
			 (byte)  0xe8, (byte)  0xcb, (byte)  0x8d, (byte)  0x01, (byte)  0x02, (byte)  0x04, (byte)  0x08, (byte)  0x10, (byte)  0x20, (byte)  0x40, 
			 (byte)  0x80, (byte)  0x1b, (byte)  0x36, (byte)  0x6c, (byte)  0xd8, (byte)  0xab, (byte)  0x4d, (byte)  0x9a, (byte)  0x2f, (byte)  0x5e, 
			 (byte)  0xbc, (byte)  0x63, (byte)  0xc6, (byte)  0x97, (byte)  0x35, (byte)  0x6a, (byte)  0xd4, (byte)  0xb3, (byte)  0x7d, (byte)  0xfa, 
			 (byte)  0xef, (byte)  0xc5, (byte)  0x91, (byte)  0x39, (byte)  0x72, (byte)  0xe4, (byte)  0xd3, (byte)  0xbd, (byte)  0x61, (byte)  0xc2, 
			 (byte)  0x9f, (byte)  0x25, (byte)  0x4a, (byte)  0x94, (byte)  0x33, (byte)  0x66, (byte)  0xcc, (byte)  0x83, (byte)  0x1d, (byte)  0x3a, 
			 (byte)  0x74, (byte)  0xe8, (byte)  0xcb, (byte)  0x8d, (byte)  0x01, (byte)  0x02, (byte)  0x04, (byte)  0x08, (byte)  0x10, (byte)  0x20, 
			 (byte)  0x40, (byte)  0x80, (byte)  0x1b, (byte)  0x36, (byte)  0x6c, (byte)  0xd8, (byte)  0xab, (byte)  0x4d, (byte)  0x9a, (byte)  0x2f, 
			 (byte)  0x5e, (byte)  0xbc, (byte)  0x63, (byte)  0xc6, (byte)  0x97, (byte)  0x35, (byte)  0x6a, (byte)  0xd4, (byte)  0xb3, (byte)  0x7d, 
			 (byte)  0xfa, (byte)  0xef, (byte)  0xc5, (byte)  0x91, (byte)  0x39, (byte)  0x72, (byte)  0xe4, (byte)  0xd3, (byte)  0xbd, (byte)  0x61, 
			 (byte)  0xc2, (byte)  0x9f, (byte)  0x25, (byte)  0x4a, (byte)  0x94, (byte)  0x33, (byte)  0x66, (byte)  0xcc, (byte)  0x83, (byte)  0x1d, 
			 (byte)  0x3a, (byte)  0x74, (byte)  0xe8, (byte)  0xcb, (byte)  0x8d, (byte)  0x01, (byte)  0x02, (byte)  0x04, (byte)  0x08, (byte)  0x10, 
			 (byte)  0x20, (byte)  0x40, (byte)  0x80, (byte)  0x1b, (byte)  0x36, (byte)  0x6c, (byte)  0xd8, (byte)  0xab, (byte)  0x4d, (byte)  0x9a, 
			 (byte)  0x2f, (byte)  0x5e, (byte)  0xbc, (byte)  0x63, (byte)  0xc6, (byte)  0x97, (byte)  0x35, (byte)  0x6a, (byte)  0xd4, (byte)  0xb3, 
			 (byte)  0x7d, (byte)  0xfa, (byte)  0xef, (byte)  0xc5, (byte)  0x91, (byte)  0x39, (byte)  0x72, (byte)  0xe4, (byte)  0xd3, (byte)  0xbd, 
			 (byte)  0x61, (byte)  0xc2, (byte)  0x9f, (byte)  0x25, (byte)  0x4a, (byte)  0x94, (byte)  0x33, (byte)  0x66, (byte)  0xcc, (byte)  0x83, 
			 (byte)  0x1d, (byte)  0x3a, (byte)  0x74, (byte)  0xe8, (byte)  0xcb
	};
	 
	private byte[][] key;
	private int round;
	public Key(byte[] k){
		key = new byte[4][44];
		//Start at Zero because you have to add round key before starting
		round = 0;
    	expandKey(k);
    	printKeyHex(key);
	}
	
	private void expandKey(byte[] k){
		int index = 0;
		//Fill key matrix column by column
		for(int row = 0; row < 4; row++){
			for(int col = 0; col < 4; col++){
				key[col][row] = k[index++]; 
			}
		}
		for(int r = 4; r < key[0].length; r+=4){
			//Copy the first 4 columns into Words 0 - 3.
			byte[] w0 = new byte[4];
			for(int i = 0; i < 4; i++){
				w0[i] = key[i][r-4];
			}
			byte[] w1 = new byte[4];
			for(int i = 0; i < 4; i++){
				w1[i] = key[i][r-3];
			}
			byte[] w2 = new byte[4];
			for(int i = 0; i < 4; i++){
				w2[i] = key[i][r-2];
			}
			byte[] w3 = new byte[4];
			byte[] gw3 = new byte[4];
			for(int i = 0; i < 4; i++){
				w3[i] = key[i][r-1];
				gw3[i] = key[i][r-1];
			}
			
			//Rotate w3 1 place left
			State.shiftRow(gw3, 1);
			
			//Substitute Bytes on w3.
			SBox.subByte(gw3);
			
			//Get the round constant (increments round as well)		
			byte[] rconi = new byte[] {rcon[++round], (byte) 0x00, (byte) 0x00, (byte) 0x00};
			
			//XOR w3 with rcon of the round
			for(int i = 0; i < gw3.length; i++){
				gw3[i] = (byte) (gw3[i] ^ rconi[i]);
			}
			
			//Create w4 = w0 XOR G(w3)
			byte[] w4 = new byte[4];
			for(int i = 0; i < w4.length; i++){
				w4[i] = (byte) (gw3[i] ^ w0[i]);
			}
			
			//Create w5 = w4 XOR W1
			byte[] w5 = new byte[4];
			for(int i = 0; i < w4.length; i++){
				w5[i] = (byte) (w4[i] ^ w1[i]);
			}
			
			//Create w6 = w5 XOR W2
			byte[] w6 = new byte[4];
			for(int i = 0; i < w6.length; i++){
				w6[i] = (byte) (w5[i] ^ w2[i]);
			}
			
			//Create w7 = w6 XOR W3
			byte[] w7 = new byte[4];
			for(int i = 0; i < w7.length; i++){
				w7[i] = (byte) (w6[i] ^ w3[i]);
			}
			
			
			//Copy the words back into the key
			for(int i = 0; i  < w4.length; i++){
				key[i][r] = w4[i];
			}
			for(int i = 0; i  < w5.length; i++){
				key[i][r+1] = w5[i];
			}
			for(int i = 0; i  < w6.length; i++){
				key[i][r+2] = w6[i];
			}
			for(int i = 0; i  < w7.length; i++){
				key[i][r+3] = w7[i];
			}
			
		}
	}
	
	/**
	 * Returns the key for the given round. 
	 * @return
	 */
	public byte[][] getRoundKey(int roundNumber){
		byte[][] roundKey = new byte[4][4];
		int start = roundNumber * 4;
		int end = start + 4;
		//Take from the key from roundNumber to round number + 1
		for(int col = start, i = 0; col < end; col++, i++){
			for(int row = 0, j = 0; row < key.length; row++, j++){
				roundKey[j][i] = key[row][col];
			}
		}
		
		return roundKey;
	}
	
	
	/**
	 * Prints the state in Hexadecimal Format
	 */
	public static void printKeyHex(byte[][] matrix){
		System.out.println("==============================KEY================================");
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				System.out.print(String.format("%02X",  matrix[i][j] & 0xFF) + " ");
			}
			System.out.println();
		}
	}
	
	@Override
	public String toString(){
		String result = "";
		for(int i = 0; i < key.length; i++){
			for(int j = 0; j < key[0].length; j++){
				result += (String.format("%02X",  key[i][j] & 0xFF) + " ");
			}
			result += "\n";
		}
		return result;
	}
	
}
