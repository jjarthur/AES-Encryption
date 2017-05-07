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
        for (int i = 0; i < state.length; i++){
            for (int j = 0; j < state[0].length; j++){
                state[i][j] = SBox.getByte(SBox.getSbox(), bitSetToInt(state[i][j])[0], bitSetToInt(state[i][j])[1]);
            }
        }
    }

    public int[] bitSetToInt(BitSet bs){
        //Converting the first for bits into one index, and the second four into the other index. Checking for 0 to catch null exceptions.
        return new int[]{ bs.get(0, 4).toLongArray().length != 0 ? (int)bs.get(0, 4).toLongArray()[0] : 0, bs.get(4, 8).toLongArray().length != 0 ? (int)bs.get(4, 8).toLongArray()[0] : 0 };
    }

    public void shiftRows(){
        for (int i = 0; i < 4; i++){
            switch(i){
                case 0: break;
                case 1: rotWord(state[i]);break;
                case 2: rotWord(state[i]);rotWord(state[i]);break;
                case 3: rotWord(state[i]);rotWord(state[i]);rotWord(state[i]);break;
            }
        }
    }

    public BitSet[] rotWord(BitSet[] word){
        BitSet temp = word[0];

        for (int i = 0; i < word.length-1; i++){
            word[i] = word[i+1];
        }

        word[word.length-1] = temp;
        return word;
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
