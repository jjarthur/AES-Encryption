import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public abstract class Operation {

    protected boolean encrypt; //True if encrypting, false if decrypting
    protected final int rounds = 10;
    protected BitSet[][] sbox; //16 by 16 byte matrix
    protected BitSet[][] inverseSbox; //16 by 16 byte matrix
    protected BitSet[] transSize; //Between 1 and 16 bytes (0 if not applicable)
    protected BitSet[] plainText; //32 bytes
    protected BitSet[] key; //16 bytes
    protected BitSet[] iv; //16 bytes (0 if not applicable)
    protected Matrix state; //Current state
    protected List<BitSet[][]> keySchedule;

    public Operation(boolean encrypt, BitSet[] transSize, BitSet[] plainText, BitSet[] key, BitSet[] iv){
        this.encrypt = encrypt;
        this.transSize = transSize;
        this.plainText = plainText;
        this.key = key;
        this.iv = iv;

        sbox = SBox.getSbox();
        inverseSbox = SBox.getInverseSbox();
        state = new Matrix(plainText, plainText.length/4);
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
        state.subBytes();
    }

    public void shiftRows(){
        state.shiftRows();
    }

    public void mixColumns(){
        state.mixColumns();
    }

    public void addRoundKey(){
        state.addRoundKey();
    }

    public List<BitSet[][]> keyExpansion(){
        List<BitSet[][]> keySchedule = new ArrayList<>();

        for (int i = 0; i < rounds; i++){

        }

        return keySchedule;
    }
}
