/**
 * Author: Joshua Crompton and James Arthur
 * Date: May 13, 2017
 * Class: OFB.java
 * Description: This class performs encryption using output feedback mode.
 */

public class OFB {

	private int numberOfBlocks; //Number of blocks in the algorithm
	private boolean encrypting; //True if encrypting, false if decrypting
	private byte[] iv; //16 bytes (0 if not applicable)
	private byte[] cipherInput; //Input into cipher
	private byte[] cipherOutput; //Output from cipher
	private int cipherTextIndex; //Index of cipher text
	private int transmissionSize; //Between 1 and 16 bytes (0 if not applicable)
	private Key key; //16 bytes

	//encrypting, plainText, key, initialisationVector
    public OFB(boolean encrypt, byte[] plaintext, byte[] keyA, byte[] initialisationVector, int tranSize) throws Exception{
    	encrypting = encrypt;
    	
    	//Fill Key by Column
    	key = new Key(keyA);   	
    	
    	//Create input and output "stream"
    	cipherInput = plaintext;
    	cipherOutput = new byte[plaintext.length];    	
    	cipherTextIndex = 0;
    	
    	this.iv = initialisationVector;
    	this.transmissionSize = tranSize;
    	
    	//Get the number of blocks of input data
    	numberOfBlocks = plaintext.length / iv.length;
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
    	//Use the iv as nonce
    	byte[] nonce = iv;
    	
    	for(int round = 0; round < numberOfBlocks; round++){
	    	//Encrypt the nonce
	    	State currentState = new State(nonce);
	
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
	    	
			//nonce for next block is the encryption of the previous nonce
			nonce = currentState.getStateArray();
			
			//XOR output of encryption (now assigned as nonce) with the Plaintext
			for(int i = 0; i < nonce.length; i++){
				cipherOutput[cipherTextIndex] = (byte) (nonce[i] ^ cipherInput[cipherTextIndex]);
				cipherTextIndex++;
			}
    	}
        //Print the states (cipher text)
    	printStates();
    }
    
    public void decrypt() throws Exception{
    	encrypt();
    }

    /**
	 * Prints the cipher text which is stored in the state
	 */
	private void printStates() {
		for(int i = 0; i < cipherOutput.length; i++){
			System.out.print(String.format("%02X",  cipherOutput[i]) + " ");
		}
		
	}
	
	public byte[] getState(){
		return cipherOutput;
	}

}
