package PersonData;

import java.io.Serializable;

public class Coordinates implements Serializable {
    public Double x; //Максимальное значение поля: 57
    public Double y; //Значение поля должно быть больше -488
    public Coordinates (Double x, Double y ) throws AllException {
        this.x = x; if (x>57) throw new AllException("Ошибка, координата Х не может быть больше 57");
        this.y = y; if (y<-488) throw new AllException("Ошибка, координата Y не может быть меньше  -488");
    }

    public Coordinates(Object coordinates, Object coordinates1) {
    }

    public Coordinates(Object coordinates) {
    }

    public Double getX(){
        return x;
    }
    public Double getY(){
        return y;
    }
    public void setX(Double aLong){
        this.x=x;
    }
    public void setY(Double aLong){
        this.y=y;
    }
    void setXY(Double xn, Double yn){
        x = xn;
        y = yn;
    }
    public Coordinates(){
        this.x=x;
        this.y = y;
    }
    @Override
    public String toString() {
        return (
                "x =  " + x + ", y = "+ y);
    }
}