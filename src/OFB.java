import java.util.BitSet;

public class OFB extends Operation {

    public OFB(boolean encrypt, BitSet[][] sbox, BitSet[] transSize, BitSet[] plainText, BitSet[] key, BitSet[] iv){
        super(encrypt, sbox, transSize, plainText, key, iv);
    }

    public void encrypt(){

    }

    public void decrypt(){

    }
}
