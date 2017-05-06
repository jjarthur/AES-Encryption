import java.util.BitSet;

public abstract class Operation {

    protected boolean encrypt; //True if encrypting, false if decrypting
    protected BitSet[][] sbox; //16 by 16 byte matrix
    protected BitSet[] transSize; //Between 1 and 16 bytes (0 if not applicable)
    protected BitSet[] plainText; //32 bytes
    protected BitSet[] key; //16 bytes
    protected BitSet[] iv; //16 bytes (0 if not applicable)
    protected State state; //Current state

    public Operation(boolean encrypt, BitSet[][] sbox, BitSet[] transSize, BitSet[] plainText, BitSet[] key, BitSet[] iv){
        this.encrypt = encrypt;
        this.sbox = sbox;
        this.transSize = transSize;
        this.plainText = plainText;
        this.key = key;
        this.iv = iv;

        state = new State(plainText);
    }

    public abstract void encrypt();

    public abstract void decrypt();

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
}
