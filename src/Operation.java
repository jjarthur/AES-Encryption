import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public abstract class Operation {

    protected boolean encrypt; //True if encrypting, false if decrypting
    protected final int rounds = 10;
    protected SBox sbox; //16 by 16 byte matrix
    protected SBox inverseSbox; //16 by 16 byte matrix
    protected BitSet[] transSize; //Between 1 and 16 bytes (0 if not applicable)
    protected BitSet[] plainText; //32 bytes
    protected BitSet[] key; //16 bytes
    protected BitSet[] iv; //16 bytes (0 if not applicable)
    protected List<Matrix> states; //Current states
    protected List<BitSet[][]> keySchedule;

    public Operation(boolean encrypt, BitSet[] transSize, BitSet[] plainText, BitSet[] key, BitSet[] iv){
        this.encrypt = encrypt;
        this.transSize = transSize;
        this.plainText = plainText;
        this.key = key;
        this.iv = iv;

        sbox = new SBox(false);
        inverseSbox = new SBox(true);
        states = initStates();
        keySchedule = keyExpansion();
    }

    public abstract void encrypt();

    public abstract void decrypt();

    public void run() {
        if (encrypt){
            encrypt();
        }
        else{
            //ecb.decrypt();
        }
    }

    public void subBytes(){
        for (Matrix state : states){
            state.subBytes();
        }
    }

    public void shiftRows(){
        for (Matrix state : states){
            state.shiftRows();
        }
    }

//    public void mixColumns(){
//        state.mixColumns();
//    }
//
//    public void addRoundKey(){
//        state.addRoundKey();
//    }

    public List<Matrix> initStates(){
        List<Matrix> states = new ArrayList<>();
        int blocks = plainText.length/16; // 32/16 = 2
        BitSet[][] tempBSArray = new BitSet[blocks][16]; //Stores the data that will be converted into states

        for (int i = 0; i < blocks; i++){
            for (int j = 0; j < 16; j++) {
                tempBSArray[i][j] = plainText[i*16+j];
            }
        }

        for (int i = 0; i < blocks; i++){
            states.add(new Matrix(tempBSArray[i], 4, 4));
        }

        return states;
    }

    public List<BitSet[][]> keyExpansion(){
        List<BitSet[][]> keySchedule = new ArrayList<>();

        for (int i = 0; i < rounds; i++){

        }

        return keySchedule;
    }

    public void outputStates(){
        for (Matrix state : states){
            state.outputMatrix();
        }
    }
}
