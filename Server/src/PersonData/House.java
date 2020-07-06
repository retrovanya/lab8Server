package PersonData;

import java.io.Serializable;

public class House implements Serializable {
    public  String name; //Поле может быть null
    public Integer year; //Значение поля должно быть больше 0
    public Integer numberOfLifts; //Значение поля должно быть больше 0
    public House(String name, Integer year, Integer numberOfLifts) throws AllException {
        this.name = name; if (name==null) throw new AllException("поле name не может быть null");
        this.year = year; if (year<1) throw new AllException("Ошибка, возраст дома должен быть положительным");
        this.numberOfLifts = numberOfLifts; if (numberOfLifts<1) throw new AllException("Ошибка, в доме должен быть хотя бы один лифт");
    }
    public String getHouseName() {
        return name;
    }
    public void setName (String name){
        this.name = name;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear (Integer year){
        this.year = year;
    }
    public Integer getNumberOfLifts() {
        return numberOfLifts;
    }
    public void setNumberOfLifts(Integer numberOfLifts){
        this.numberOfLifts = numberOfLifts;
    }
    public House(){
        this.name=name;
        this.year = year;
        this.numberOfLifts = numberOfLifts;
    }
    @Override
    public String toString() {
        return (
                "Вид дома: " + name + ", возраст дома = "+ year +" лет, количество лифтов = "+ numberOfLifts);
    }
}
