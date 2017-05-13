package joshscode;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class CBC {

	private int numberOfBlocks;
	private boolean encrypting;
	private State[] blocks;
	private Key key;
	private byte[] iv;
	
	/**
	 * Input to the CBC is the XOR of the next Block of Plain Text and previous ciphertext
	 * @param encrypt
	 * @param plaintext
	 * @param keyA
	 * @throws Exception
	 */
    public CBC(boolean encrypt, byte[] IV, byte[] plaintext, byte[] keyA) throws Exception{
    	encrypting = encrypt;
    	
    	this.iv = IV;
    	
    	//Fill Key by Column
    	key = new Key(keyA);   	
    	
    	//Get the number of blocks of input data
    	numberOfBlocks = plaintext.length / 16;
    	  	
    	//Create a state array
    	blocks = new State[numberOfBlocks+1];
    	//Put the initialisation vector in the first state
    	blocks[0] = new State(iv);
    	
    	byte[] block = null;
    	for(int i = 1; i < numberOfBlocks+1; i++){
    		//Get the byte[] representing the block
    		block = new byte[16];
    		int index = 0;
    		for(int j = (i-1)*16; j < (((i-1)*16)+16); j++){
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
    	//Starting with block 1
    	//Remember that blocks[0] is the Initialisation vector
    	for(int i = 1; i < numberOfBlocks+1; i++){
    		//XOR with the previous state
    		State currentState = blocks[i];
    		currentState.XOR(blocks[i-1].getStateMatrix());
    		//Encrpyt state 
			//Add Round key first
			byte[][] rk = key.getRoundKey(0);
			//Key.printKeyHex(rk);
			currentState.addRoundKey(rk);
			//128 Bit key uses 10 rounds
			for(int round = 1; round <= 10; round++){
				currentState.subBytes();
				currentState.shiftRows();
		        //Don't mix columns in the 10th round.
		        if(round != 10){
		        	currentState.mixColumns(false);
		        }
		        rk = key.getRoundKey(round);
		        //Key.printKeyHex(rk);
		        currentState.addRoundKey(rk);
			}    	
    	}    	
        //Print the states (cipher text)
    	printStates();
    }
    
    public void decrypt() throws Exception{
    	//Starting with last block of ciphertext
    	//Remember that blocks[0] is the Initialisation vector
    	for(int i = numberOfBlocks; i > 0 ; i--){
    		State currentState = blocks[i];
    		//Decrypt state 
        	byte[][] rk;
    		for(int round = 10; round >=1; round--){
        		rk = key.getRoundKey(round);
        		currentState.addRoundKey(rk);
        		if(round != 10){
        			currentState.mixColumns(true);
        		}
        		currentState.inverseShiftRows();
        		currentState.inverseSubBytes();
    		}
    		
    		rk = key.getRoundKey(0);
    		currentState.addRoundKey(rk);    		

    		//XOR with the previous state
    		currentState.XOR(blocks[i-1].getStateMatrix());
    		
    	}    	
        //Print the states (cipher text)
    	printStates();
    }

    /**
	 * Prints the cipher text which is stored in the state
	 */
	private void printStates() {
		//For each block. Start from 1 because blocks[0] is the
		//initialisation vector.
		for(int i = 1; i < numberOfBlocks+1; i++){
			blocks[i].printStateHexFlat();
		}
	}
	
	/**
	 * Gets the entire state in a single array.
	 * @return
	 */
	public byte[] getState(){
		byte[] result = blocks[1].getStateArray(); 
		for(int i = 1; i < numberOfBlocks; i++){
			byte[] b1 = blocks[i].getStateArray();
			byte[] b2 = blocks[i+1].getStateArray();
			result = Arrays.copyOf(b1, b1.length + b2.length);
			System.arraycopy(b2, 0, result, b1.length, b2.length);
		}
		return result;
	}

}
