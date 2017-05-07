//Main class
//Author: James Arthur
//Last edit: 06/05/2017

/**
 * This AESInterface class contains main method of the program which is used to carry out a
 * series of operations. It also contains methods for receiving user input and displaying
 * a summary of the output.
 */

import java.util.*;
import java.io.*;

public class AESInterface {

    //Private attributes
    private static String file;
    private int mode; //0 = ECB, 1 = CFB, 2 = CBC, 3 = OFB
    private boolean encrypt = true; //True if encrypting, false if decrypting
    private BitSet[] transSize = new BitSet[16]; //Between 1 and 16 bytes (0 if not applicable)
    private BitSet[] plainText = new BitSet[32]; //32 bytes
    private BitSet[] key = new BitSet[16]; //16 bytes
    private BitSet[] iv = new BitSet[16]; //16 bytes (0 if not applicable)
    private Operation ecb; //Electronic Codebook mode
    private Operation cbc; //Cipher Block Chaining mode
    private Operation cfb; //Cipher Feedback mode
    private Operation ofb; //Output Feedback mode

    //Public methods

    /**
     * Calls run().
     */
    public static void main(String[] args){
        AESInterface aes = new AESInterface();
        file = args[0];

        aes.run();
    }

    public void run(){
        getInput();

        switch (mode) {
            case 0:
                ecb = new ECB(true, transSize, plainText, key, iv);
                ecb.run();
        }
    }

    public void getInput(){

        try {
            FileReader fr = new FileReader(new File(file));
            Scanner scanner = new Scanner(fr);
            String tempString;

            //Encrypt or decrypt
            if (scanner.nextInt() == 0){
                encrypt = false;
            }

            //Getting mode
            mode = scanner.nextInt();
            scanner.nextLine(); //Clear scanner buffer

            //Getting transSize
            tempString = scanner.nextLine().replaceAll("\\s","");
            if (!tempString.equals("0")){
                transSize = SBox.hexStringToBitSetArray(tempString, transSize.length);
            }

            //Getting plainText
            plainText = SBox.hexStringToBitSetArray(scanner.nextLine().replaceAll("\\s",""), plainText.length);

            //Getting key
            key = SBox.hexStringToBitSetArray(scanner.nextLine().replaceAll("\\s",""), key.length);

            //Getting initialization vector
            tempString = scanner.nextLine().replaceAll("\\s","");
            if (!tempString.equals("0")){
                iv = SBox.hexStringToBitSetArray(tempString, iv.length);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
