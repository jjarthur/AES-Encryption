package joshscode;

import java.nio.ByteBuffer;

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
	 */
	public void operate() {
		if(encrypting){
			encrypt();
		}
		else{
			decrypt();
		}
		
	}

    public void encrypt(){
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
    	        	s.mixColumns();
    	        }
    	        rk = key.getRoundKey(i);
    	        //Key.printKeyHex(rk);
    	        s.addRoundKey(rk);
    		}
    	}
        //Print the states (cipher text)
    	printStates();
    }
    
    public void decrypt(){
		//For each block
    	for(State s : blocks){
    		//Add the last Round key first
    		byte[][] rk = key.getRoundKey(10);
    		//Key.printKeyHex(rk);
    		s.addRoundKey(rk);
    		//128 Bit key uses 10 rounds
    		for(int i = 9; i <= 0; i--){
    			//s.inverseShiftRows();
    			//s.inverseSubBytes();
    			rk = key.getRoundKey(i);
    	        //Key.printKeyHex(rk);
    	        s.addRoundKey(rk);
    	        //Don't mix columns in the last round.
    	        if(i != 0){
    	        	s.mixColumns();
    	        }
    		}
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

	

	

}
