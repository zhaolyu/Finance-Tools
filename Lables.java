/**
 * Created by Josue on 4/18/2016.
 */
public enum Lables {
    DATE(0), OPEN(1), HIGH(2), LOW(3), CLOSE(4), BULLISH(0), BEARISH(1);
    private int value;

    Lables(int value) {
        this.value = value;
    }

    public int val(){
        return this.value;
    }
}
