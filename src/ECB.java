import java.util.BitSet;

public class ECB extends Operation {

    public ECB(boolean encrypt, BitSet[][] sbox, BitSet[] transSize, BitSet[] plainText, BitSet[] key, BitSet[] iv){
        super(encrypt, sbox, transSize, plainText, key, iv);
    }

    public void encrypt(){
        subBytes();
        shiftRows();
        mixColumns();
        addRoundKey();
    }

    public void decrypt(){

    }

}
