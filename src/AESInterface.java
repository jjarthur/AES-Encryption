/**
 * Author: Joshua Crompton and James Arthur
 * Date: May 12, 2017
 * Class: AESInterface.java
 * Description: This is the main entry point to 
 * 				the program. It reads the input 
 * 				file and initializes the encryption mode.
 */

import java.io.FileReader;
import java.util.Scanner;

public class AESInterface {

	
	private static String FILE = "inputOFBd.txt";
	private boolean encrypting;
	private int mode;
	private int transmissionSize;
	private byte[] plainText;
	private byte[] key;
	private byte[] initialisationVector;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length > 0){
			FILE = args[0];
		}
		
		AESInterface aes = new AESInterface();
		try {
			aes.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Runs AES Encryption on the given file.
	 * @throws Exception 
	 */
	private void run() throws Exception {
		//Read input
		readFile(FILE);
		switch(mode){
			case 0: ECB ecb = new ECB(encrypting, plainText, key);
					ecb.operate();
					break;
			case 1: CFB cfb = new CFB(encrypting, plainText, key, initialisationVector, transmissionSize);
					cfb.operate();
					break;
			case 2: CBC cbc = new CBC(encrypting, plainText, key, initialisationVector);
					cbc.operate();
					break;
			case 3: OFB ofb = new OFB(encrypting, plainText, key, initialisationVector, transmissionSize);
					ofb.operate();
					break;
		
		}
	}

	/**
	 * @param fILE2
	 */
	private void readFile(String filePath) {
		/*
		 *  0		0 = Encrpyting
			0		MODE 0 = ECB, 1 = CFB, 2 = CBC, 3 = OFB
			0		Transmission Siz
			5A 67 F0 12 CF 98 AB CD 00 E4 35 FF 01 35 78 91 FA C2 CF 98 AB CD 00 E4 35 FF 01 35 00 E4 35 FF      PLAINTEXT
			00 E4 35 FF 01 35 78 91 AB CD 00 E4 67 F0 12 CF														 KEY
			0																									 Initilisation Vector
		 */
		try(Scanner scanner = new Scanner(new FileReader(filePath))){
			//Read in whether encrypting or decrypting
            if (scanner.nextInt() == 0){
                encrypting = true;
            }

            //Getting mode
            mode = scanner.nextInt();
            scanner.nextLine(); //Clear scanner buffer
            
            //Get Transmission Size
            transmissionSize = scanner.nextInt();
            scanner.nextLine();
            
            //Get the plaintext removing spaces.
            String input = scanner.nextLine().replaceAll("\\s", "");;
            plainText = hexStringToByteArray(input);
            
            input = scanner.nextLine().replaceAll("\\s", "");
            key = hexStringToByteArray(input);
            
            input = scanner.nextLine().replaceAll("\\s", "");
            if(input.length() > 1){
            	initialisationVector = hexStringToByteArray(input);
            }
            
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
