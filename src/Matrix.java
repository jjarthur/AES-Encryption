import java.util.BitSet;

public class Matrix {
    protected BitSet[][] matrix;

    public Matrix(){
        matrix = new BitSet[4][4];
    }

    public Matrix(BitSet[] bsArray, int columns){
        matrix = new BitSet[4][columns];
        matrix = arrayToMatrix(bsArray, matrix.length, matrix[0].length);
    }

    public static BitSet[][] arrayToMatrix(BitSet[] bsArray, int rows, int columns){
        BitSet[][] matrix = new BitSet[rows][columns];

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                matrix[i][j] = bsArray[i*8+j];
            }
        }

        return matrix;
    }

    public void subBytes(){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                System.out.println(bitSetToInt(matrix[i][j])[0] + "       " + bitSetToInt(matrix[i][j])[1]);
                matrix[i][j] = SBox.getByte(SBox.getSbox(), bitSetToInt(matrix[i][j])[0], bitSetToInt(matrix[i][j])[1]);
                //System.out.println(matrix[i][j]);
                System.out.println(SBox.getByte(SBox.getSbox(), bitSetToInt(matrix[i][j])[0], bitSetToInt(matrix[i][j])[1]));
                System.out.println();
            }
        }
    }

    public int[] bitSetToInt(BitSet bs){
        //Converting the first for bits into one index, and the second four into the other index. Checking for 0 to catch null exceptions.
        int x = bs.get(0, 4).toLongArray().length != 0 ? (int)bs.get(0, 4).toLongArray()[0] : 0;
        int y = bs.get(4, 8).toLongArray().length != 0 ? (int)bs.get(4, 8).toLongArray()[0] : 0;
        //System.out.println(bs);
        //System.out.println(x + "   " + y);
        return new int[]{ bs.get(4, 8).toLongArray().length != 0 ? (int)bs.get(4, 8).toLongArray()[0] : 0, bs.get(0, 4).toLongArray().length != 0 ? (int)bs.get(0, 4).toLongArray()[0] : 0 };
        //return new int[]{ y, x };
    }

    public void shiftRows(){
        for (int i = 0; i < 4; i++){
            switch(i){
                case 0: break;
                case 1: rotWord(matrix[i]); break;
                case 2: rotWord(matrix[i]);rotWord(matrix[i]); break;
                case 3: rotWord(matrix[i]);rotWord(matrix[i]); rotWord(matrix[i]); break;
            }
        }
    }

    public void mixColumns(){

    }

    public void addRoundKey(){

    }

    public BitSet[] rotWord(BitSet[] word){
        BitSet temp = word[0];

        for (int i = 0; i < word.length-1; i++){
            word[i] = word[i+1];
        }

        word[word.length-1] = temp;
        return word;
    }

    //Round must be between 1 and 10
    public BitSet rcon(int round){
        String rconString = "01 02 04 08 10 20 40 80 1b 36";
        BitSet[] rcon = new BitSet[10];
        BitSet rkey;

        //Removing white-space from rconString
        rconString = rconString.replaceAll("\\s","");
        //Creating array of round constant bitsets
        rcon = hexStringToBitSetArray(rconString, rcon.length);

        rkey = rcon[round-1];

        return rkey;
    }

    public static BitSet[] hexStringToBitSetArray(String hexString, int length){
        BitSet[] bsArray = new BitSet[length];
        String bitString = "";
        String[] hexArray = hexString.split("");

        for (int i = 0; i < hexArray.length; i++){
            bitString += hexStringToBitString(hexArray[i]);
        }


        while (bitString.length() < 8){
            bitString = "0" + bitString;
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

    public static String hexStringToBitString (String hexString){
        String bs;
        switch(hexString.toUpperCase()){ //Normalize input
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

    public void outputMatrix(){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
}
