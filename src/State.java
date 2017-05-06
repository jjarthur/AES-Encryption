import java.util.BitSet;

public class State {
    private BitSet[][] state = new BitSet[4][8];

    public State(BitSet[] bsArray){
        arrayToState(bsArray);
    }

    public void arrayToState(BitSet[] bsArray){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 8; j++){
                state[i][j] = bsArray[i*8+j];
            }
        }
    }

    public void subBytes(){

    }

    public void shiftRows(){

    }

    public void mixColumns(){

    }

    public void addRoundKey(){

    }

    public void outputState(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 8; j++){
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
    }
}
