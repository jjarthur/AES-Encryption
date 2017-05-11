import java.util.BitSet;

public class ECB extends Operation {

    public ECB(boolean encrypt, BitSet[] transSize, BitSet[] plainText, BitSet[] key, BitSet[] iv){
        super(encrypt, transSize, plainText, key, iv);
    }

    public void encrypt(){
        state.outputMatrix();
        //subBytes();
        //shiftRows();
        //mixColumns();
        //addRoundKey();
        //state.outputMatrix();
    }

    public void decrypt(){

    }

}
