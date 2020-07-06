package PersonData;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
public class CollectionOfFlats implements Serializable{
    public static LocalDateTime time=LocalDateTime.now();;
    public static HashSet<Flat> collection = new HashSet<>();
    public static HashSet<Flat> MyCollection = new HashSet<>();
    public static HashSet<Flat> Collection() throws AllException, IOException {
        return collection;
    }

    public void InfoCollection () throws IOException, AllException {
        System.out.println("Размерность коллекции равна "+ CollectionOfFlats.collection.size() +"\n"+ "Тип коллекции -  "+ CollectionOfFlats.collection.getClass() + "\n"+ "Дата инициализации: "+ CollectionOfFlats.time);
    }


}
