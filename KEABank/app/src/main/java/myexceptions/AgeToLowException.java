package myexceptions;

public class AgeToLowException extends Exception {
    int id;

    public AgeToLowException(int x){
        id = x;
    }

    public String toString(){
        return "AgeToLowException[" + id + "]";
    }
}
