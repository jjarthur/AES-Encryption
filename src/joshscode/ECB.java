package joshscode;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ECB {

	private int numberOfBlocks;
	private boolean encrypting;
	private State[] blocks;
	private Key key;
	//private int[] expandedKey;
	
    public ECB(boolean encrypt, byte[] plaintext, byte[] keyA) throws Exception{
    	encrypting = encrypt;
    	
    	//Fill Key by Column
    	key = new Key(keyA);   	
    	
    	//Get the number of blocks of input data
    	numberOfBlocks = plaintext.length / 16;
    	
    	//Create a state for each block
    	blocks = new State[numberOfBlocks];
    	for(int i = 0; i < numberOfBlocks; i++){
    		//Get the byte[] representing the block
    		byte[] block = new byte[16];
    		int index = 0;
    		for(int j = i*16; j < ((i*16)+16); j++){
    			block[index++] = plaintext[j];
    		}
    		blocks[i] = new State(block);
    	}
    	
    }
    
	private int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
	
	private byte[] toByteArray(int value) {
	     return  ByteBuffer.allocate(4).putInt(value).array();
	}
    
    /**
	 * Operates according to the encrypting bolean 
     * @throws Exception 
	 */
	public void operate() throws Exception {
		if(encrypting){
			encrypt();
		}
		else{
			decrypt();
		}
		
	}

    public void encrypt() throws Exception{
    	//For each block
    	for(State s : blocks){
    		//Add Round key first
    		byte[][] rk = key.getRoundKey(0);
    		//Key.printKeyHex(rk);
    		s.addRoundKey(rk);
    		//128 Bit key uses 10 rounds
    		for(int i = 1; i <= 10; i++){
    			s.subBytes();
    	        s.shiftRows();
    	        //Don't mix columns in the 10th round.
    	        if(i != 10){
    	        	s.mixColumns(false);
    	        }
    	        rk = key.getRoundKey(i);
    	        //Key.printKeyHex(rk);
    	        s.addRoundKey(rk);
    		}
    	}
        //Print the states (cipher text)
    	printStates();
    }
    
    public void decrypt() throws Exception{
		//For each block
    	byte[][] rk;
    	for(State s : blocks){
    		for(int i = 10; i >=1; i--){
        		rk = key.getRoundKey(i);
        		s.addRoundKey(rk);
        		if(i != 10){
        			s.mixColumns(true);
        		}
	    		s.inverseShiftRows();
	    		s.inverseSubBytes();
    		}
    		
    		rk = key.getRoundKey(0);
    		s.addRoundKey(rk);    		
    	}
        //Print the states (cipher text)
    	printStates();
    }

    /**
	 * Prints the cipher text which is stored in the state
	 */
	private void printStates() {
		//For each block
    	for(State s : blocks){
    		//Loop through the columns
    		s.printStateHexFlat();
    	}
		
	}
	
	public byte[] getState(){
		byte[] result = blocks[0].getStateArray(); 
		for(int i = 0; i < numberOfBlocks - 1; i++){
			byte[] b1 = blocks[i].getStateArray();
			byte[] b2 = blocks[i+1].getStateArray();
			result = Arrays.copyOf(b1, b1.length + b2.length);
			System.arraycopy(b2, 0, result, b1.length, b2.length);
		}
		return result;
	}

}
