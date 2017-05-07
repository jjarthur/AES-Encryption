import java.util.BitSet;

public class OFB extends Operation {

    public OFB(boolean encrypt, BitSet[] transSize, BitSet[] plainText, BitSet[] key, BitSet[] iv){
        super(encrypt, transSize, plainText, key, iv);
    }

    public void encrypt(){

    }

    public void decrypt(){

    }
}
