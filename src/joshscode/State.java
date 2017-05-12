/**
 * Author: Joshua Crompton
 * Date: May 12, 2017
 * Class: State.java
 * Description:
 */
package joshscode;

/**
 * Represents the state of the cipher.
 * State is a 4 x 4 matrix of bytes.
 * @author Josh
 *
 */
public class State {
	private byte[][] state;
	
	public State(byte[] input) throws Exception{
		if(input.length > 16) throw new Exception("Invalid State Input. Expected lenght 16. Actual length: " + input.length);
		state = new byte[4][4];
		
		int index = 0;
		//Fill state matrix column by column
		for(int row = 0; row < 4; row++){
			for(int col = 0; col < 4; col++){
				state[col][row] = input[index++]; 
			}
		}
	}
	
	
	/**
	 * Adds the round key to the state
	 */
	public void addRoundKey(byte[][] key) {
		//Loop through each column and XOR with key
		for(int col = 0; col < key.length; col++){
			for(int row = 0; row < key[0].length; row++){
				state[row][col] = (byte) (state[row][col] ^ key[row][col]);
			}
		}
		
	}
	
	
	public void subBytes(){
		for(byte[] row : state){
			SBox.subByte(row);
		}
	}
	
	/**
	 * Shifts the Rows of the state left.
	 * 1st row is left unchanged
	 * 2nd Row is shifted one place left.
	 * 3rd Row is shifted 2 places left
	 * 4th Row is Shifted 3 places left
	 */
	public void shiftRows() {
		for(int i = 0; i < state.length; i++){
			shiftRow(state[i], i);
		}
	}
	
	/**
	 * Shifts the given array left by the given number of places.
	 * Uses the fact that primitive arrays can be seen as "Pass By Reference"
	 * and copys the cahnge into the original array.
	 * @param bs
	 * @param i
	 */
	public static void shiftRow(byte[] row, int places) {
		byte[] temp = new byte[row.length];
		
		for(int i = 0; i < row.length; i++){
			int index = (((i+places) % row.length) + row.length) % row.length;
			int x = (i - places) % row.length;
			temp[i] = row[index];
		}
		//Copy Temp into original array
		System.arraycopy(temp, 0, row, 0, temp.length );
	}
	
	/**
	 * Mixes the columns of the state by multiplying by the state
	 * by the fixed matrix.
	 */
	public void mixColumns() {
		byte[][] matrix = new byte[][]{
			{(byte) 0x02, (byte) 0x03, (byte) 0x01, (byte) 0x01},
			{(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x01},
			{(byte) 0x01, (byte) 0x01, (byte) 0x02, (byte) 0x03}, 
			{(byte) 0x03, (byte) 0x01, (byte) 0x01, (byte) 0x02}  		
    	};
    	
    	//New Matrix which will replace sate
    	byte[][] result = new byte[state.length][state[0].length];
    	  	
    	//Row index for result
    	for(int i = 0; i < matrix.length; i++){
    		//Column index for result
    		for(int j = 0; j < state[0].length; j++){
    			//Loop through the ith row and multiply by the jth column
    			for(int k = 0; k < matrix[0].length; k++){
    				//If multiply by 2 shift left 1
    				if(matrix[i][k] == 0x02){
    					result[i][j] ^= GaloisLookUp.multiplyBy2(state[k][j]);
    				}
    				//If multiply by 3 shift left 1, XOR with Itself
    				else if(matrix[i][k] == 0x03){
    					result[i][j] ^= GaloisLookUp.multiplyBy3(state[k][j]);
    				}
    				//If multiply by one just add itself
    				else{
    					result[i][j] ^= state[k][j]; 
    				}
    			}
    		}
    	}
		state = result;
	}

	/**
	 * Prints the state matrix in Decimal Format
	 */
	public void printStateDec(){
		for(int i = 0; i < state.length; i++){
			for(int j = 0; j < state[0].length; j++){
				System.out.print(state[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints the state in Hexadecimal Format
	 */
	public void printStateHex(){
		for(int i = 0; i < state.length; i++){
			for(int j = 0; j < state[0].length; j++){
				System.out.print(String.format("%02X",  state[i][j] & 0xFF) + " ");
			}
			System.out.println();
		}
	}


	/**
	 * Prints the state in Hexadecimal format on a single line.
	 * Going down columns first.
	 */
	public void printStateHexFlat() {
		System.out.println("=======================State======================");
		for(int i = 0; i < state.length; i++){
			for(int j = 0; j < state[0].length; j++){
				System.out.print(String.format("%02X",  state[j][i] & 0xFF) + " ");
			}
		}
		System.out.println();
	}
	
	@Override
	public String toString(){
		String result = "";
		for(int i = 0; i < state.length; i++){
			for(int j = 0; j < state[0].length; j++){
				result += (String.format("%02X",  state[i][j] & 0xFF) + " ");
			}
			result += "\n";
		}	
		return result;
	}
}
