import java.util.BitSet;

public class ECB extends Operation {

    public ECB(boolean encrypt, BitSet[] transSize, BitSet[] plainText, BitSet[] key, BitSet[] iv){
        super(encrypt, transSize, plainText, key, iv);
    }

    public void encrypt(){
        System.out.println("---Input---");
        outputStates();
        System.out.println("---SubBytes()---");
        subBytes();
        outputStates();
        System.out.println("---ShiftRows()---");
        shiftRows();
        outputStates();
//        System.out.println("---MixColumns()---");
//        mixColumns();
//        outputStates();
//        System.out.println("---AddRoundKey()---");
//        addRoundKey();
//        outputStates();
    }

    public void decrypt(){

    }

}
