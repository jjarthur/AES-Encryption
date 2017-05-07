import java.util.BitSet;

public class ECB extends Operation {

    public ECB(boolean encrypt, BitSet[] transSize, BitSet[] plainText, BitSet[] key, BitSet[] iv){
        super(encrypt, transSize, plainText, key, iv);
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
