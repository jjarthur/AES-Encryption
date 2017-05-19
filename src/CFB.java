/**
 * Author: Joshua Crompton and James Arthur
 * Date: May 13, 2017
 * Class: CFB.java
 * Description: This class performs encryption using cipher feedback mode.
 */

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class CFB {

	private int numberOfBlocks; //Number of blocks in the algorithm
	private int transmissionSize; //Between 1 and 16 bytes (0 if not applicable)
	private boolean encrypting; //True if encrypting, false if decrypting
	private byte[] cipherInput; //Input into cipher
	private byte[] cipherOutout;  //Output from cipher
	private int cipherTextIndex; //Index of cipher text
	private Key key; //16 bytes
	private byte[] iv; //16 bytes (0 if not applicable)
	
    public CFB(boolean encrypt, byte[] plaintext, byte[] keyA, byte[] initialisationVector, int transMissionSize) throws Exception{
    	encrypting = encrypt;
    	
    	//Fill Key by Column
    	key = new Key(keyA);   	
    	
    	this.iv = initialisationVector;
    	this.transmissionSize = transMissionSize;
    	
    	//Get the number of blocks of input data
    	//numberOfBlocks = plaintext.length / 16;
    	
    	cipherInput = plaintext;
    	//Create cipher text
    	cipherOutout = new byte[cipherInput.length];
    	cipherTextIndex = 0;
    	
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
    	//Copy the initialisation vector into the shift register. Using list so we can
    	//access every element. Tried Queue, but was inconvenient.
    	List<Byte> shiftRegister = new ArrayList<Byte>();
    	for(int i = 0; i < iv.length; i++){
    		shiftRegister.add(iv[i]);
    	}
    	int numberOfRounds = (cipherInput.length / transmissionSize);
    	for(int round = 0; round < numberOfRounds; round++){
	    	//Create a state containing the contents of the shift register.
	    	//Unbox the shift register.
	    	byte[] stateInput = new byte[shiftRegister.size()];
	    	for(int i = 0; i < shiftRegister.size(); i++){
	    		stateInput[i] = shiftRegister.get(i);
	    	}
	    	//Create state object    	  	
	    	State currentState = new State(stateInput);
	    	
	    	//Encrypt the state (shift register)
	    	//Add Round key first
			byte[][] rk = key.getRoundKey(0);
			//Key.printKeyHex(rk);
			currentState.addRoundKey(rk);
			//128 Bit key uses 10 rounds
			for(int i = 1; i <= 10; i++){
				currentState.subBytes();
				currentState.shiftRows();
		        //Don't mix columns in the 10th round.
		        if(i != 10){
		        	currentState.mixColumns(false);
		        }
		        rk = key.getRoundKey(i);
		        //Key.printKeyHex(rk);
		        currentState.addRoundKey(rk);
			}
			
			//Get the output from encryption
			byte[] encryptionOutput = currentState.getStateArray();
			//Take s bits of the encryption outout
			byte[] transmissionBits = new byte[transmissionSize];
			System.arraycopy(encryptionOutput, 0, transmissionBits, 0, transmissionSize);
			
			//XOR transmission bits with the plaintext. And store in the cipherText
			for(int i = 0; i < transmissionSize; i++){
				byte b = (byte) (transmissionBits[i] ^ cipherInput[cipherTextIndex]);
				cipherOutout[cipherTextIndex] = b;
				//Remove the front Element of the shift register
				shiftRegister.remove(0);
				//Add the ciphertext into the end shift register
				shiftRegister.add(b);			
				cipherTextIndex++;
			}
    	}		
    	printStates();    	
    }
    
    public void decrypt() throws Exception{
    	//Copy the initialisation vector into the shift register. Using list so we can
    	//access every element. Tried Queue, but was inconvenient.
    	List<Byte> shiftRegister = new ArrayList<Byte>();
    	for(int i = 0; i < iv.length; i++){
    		shiftRegister.add(iv[i]);
    	}
    	int numberOfRounds = (cipherInput.length / transmissionSize);
    	for(int round = 0; round < numberOfRounds; round++){
	    	//Create a state containing the contents of the shift register.
	    	//Unbox the shift register.
	    	byte[] stateInput = new byte[shiftRegister.size()];
	    	for(int i = 0; i < shiftRegister.size(); i++){
	    		stateInput[i] = shiftRegister.get(i);
	    	}
	    	//Create state object    	  	
	    	State currentState = new State(stateInput);
	    	
	    	//Decrpytion performs encyption the state (shift register)
	    	//Add Round key first
			byte[][] rk = key.getRoundKey(0);
			//Key.printKeyHex(rk);
			currentState.addRoundKey(rk);
			//128 Bit key uses 10 rounds
			for(int i = 1; i <= 10; i++){
				currentState.subBytes();
				currentState.shiftRows();
		        //Don't mix columns in the 10th round.
		        if(i != 10){
		        	currentState.mixColumns(false);
		        }
		        rk = key.getRoundKey(i);
		        //Key.printKeyHex(rk);
		        currentState.addRoundKey(rk);
			}	
			
			//Get the output from encryption
			byte[] encryptionOutput = currentState.getStateArray();
			//Take s bits of the encryption outout
			byte[] transmissionBits = new byte[transmissionSize];
			System.arraycopy(encryptionOutput, 0, transmissionBits, 0, transmissionSize);
			
			//XOR transmission bits with the plaintext. And store in the cipherText
			for(int i = 0; i < transmissionSize; i++){
				byte b = (byte) (transmissionBits[i] ^ cipherInput[cipherTextIndex]);
				cipherOutout[cipherTextIndex] = b;
				//Remove the front Element of the shift register
				shiftRegister.remove(0);
				//Add the ciphertext into the end shift register
				shiftRegister.add(cipherInput[cipherTextIndex]);			
				cipherTextIndex++;
			}
    	}		
    	printStates();  

    }

    /**
	 * Prints the cipher text which is stored in the state
	 */
	private void printStates() {
		for(int i = 0; i < cipherOutout.length; i++){
			System.out.print(String.format("%02X",  cipherOutout[i]) + " ");
		}
		
	}
	
	public byte[] getState(){
		return cipherOutout;
	}

}
