package bullscows;

public class BullsAndCowsException extends Exception{
    private String msg;
    public BullsAndCowsException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}