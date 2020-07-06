package PersonData;

public class AllException extends Exception {
    private Double number;
    public Double getNumber(){return number;}
    public AllException(String message){

        super(message);

    }
}