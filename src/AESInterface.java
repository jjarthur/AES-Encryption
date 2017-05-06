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
    private BitSet[][] sbox = new BitSet[16][16]; //16 by 16 byte matrix
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
        initSbox();

        switch (mode) {
            case 0:
                ecb = new ECB(encrypt, sbox, transSize, plainText, key, iv);
                if (encrypt){
                    ecb.encrypt();
                }
                else{
                    //ecb.decrypt();
                }
        }
    }

    public void initSbox(){
        //Setting S-Box matrix
        sbox = SBox.arrayToSbox(hexStringToBitSetArray(SBox.getSboxString().replaceAll("\\s",""), 256));
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
                transSize = hexStringToBitSetArray(tempString, transSize.length);
            }

            //Getting plainText
            plainText = hexStringToBitSetArray(scanner.nextLine().replaceAll("\\s",""), plainText.length);

            //Getting key
            key = hexStringToBitSetArray(scanner.nextLine().replaceAll("\\s",""), key.length);

            //Getting initialization vector
            tempString = scanner.nextLine().replaceAll("\\s","");
            if (!tempString.equals("0")){
                iv = hexStringToBitSetArray(tempString, iv.length);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public BitSet[] hexStringToBitSetArray(String hexString, int length){
        BitSet[] bsArray = new BitSet[length];
        String bitString = "";
        String[] hexArray = hexString.split("");

        for (int i = 0; i < hexArray.length; i++){
            bitString += hexStringToBitString(hexArray[i]);
        }

        for (int i = 0; i < length; i++){
            BitSet tempBS = new BitSet(8);
            for (int j = 0; j < 8; j++){
                if (bitString.charAt(i*8+j) == '1'){
                    tempBS.set(j);
                }
            }
            bsArray[i] = tempBS;
        }

        return bsArray;
    }

    public String hexStringToBitString (String hexString){
        String bs;
        switch(hexString){
            case "0": bs = "0000"; break;
            case "1": bs = "0001"; break;
            case "2": bs = "0010"; break;
            case "3": bs = "0011"; break;
            case "4": bs = "0100"; break;
            case "5": bs = "0101"; break;
            case "6": bs = "0110"; break;
            case "7": bs = "0111"; break;
            case "8": bs = "1000"; break;
            case "9": bs = "1001"; break;
            case "A": bs = "1010"; break;
            case "B": bs = "1011"; break;
            case "C": bs = "1100"; break;
            case "D": bs = "1101"; break;
            case "E": bs = "1110"; break;
            case "F": bs = "1111"; break;
            default: bs = null;
        }

        return bs;
    }
}
