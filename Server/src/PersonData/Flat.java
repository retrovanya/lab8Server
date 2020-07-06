package PersonData;

import CommandsProcessing.Receiver;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Flat implements Serializable, Comparable{

    private static Scanner scanner;
    public long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным OK!
    private String name; //Поле не может быть null, Строка не может быть пустой  OK!
    private Coordinates coordinates; //Поле не может быть null  OK!
    private java.util.Date creationDate;
    private Double area; //Максимальное значение поля: 910, Значение поля должно быть больше 0  OK!
    private long numberOfRooms; //Поле не может быть null, Значение поля должно быть больше 0  OK!
    private Furnish furnish; //Поле не может быть null  OK!
    private View view; //Поле может быть null  OK!
    private Transport transport; //Поле может быть null  OK!
    private House house; //Поле может быть null
    private String login;
    String path = "inf.json";
    public Flat(String name, long id, Coordinates coordinates1, Double area, long numberOfRooms, Furnish furnish, View view, Transport transport, House house, String login) throws AllException, IOException {
        this.id=id;
        this.name = name; if ((name.trim().length() == 0) || (name==null) ) throw new AllException("Ошибка, строка названия квартиры не может быть пустой");
        this.coordinates = coordinates1; if (coordinates==null) throw new AllException("Поле не может быть null");
        this.area = area; if (area>910) throw new AllException("Ошибка, площвдь квартиры не может превышать 910 м^2");
        this.numberOfRooms = numberOfRooms; if (numberOfRooms<1) throw new AllException("Ошибка, в квартире должна быть хотя бы одна комната");
        this.furnish = furnish; if (furnish==null) throw new AllException("Поле не может быть null");
        this.view = view; if (view==null) throw new AllException("Поле не может быть null");
        this.transport = transport; if (transport==null) throw new AllException("Поле не может быть null");
        this.house = house;
        this.login = login;
        this.creationDate = java.util.Date.from(Instant.now());; if (creationDate==null) throw new AllException("Поле не может быть null");



    }
    public Flat(String name, Coordinates coordinates1, Double area, long numberOfRooms, Furnish furnish, View view, Transport transport, House house) throws AllException, IOException {
        this.id = randomId();
        this.name = name; if ((name.trim().length() == 0) || (name==null) ) throw new AllException("Ошибка, строка названия квартиры не может быть пустой");
        this.coordinates = coordinates1; if (coordinates==null) throw new AllException("Поле не может быть null");
        this.area = area; if (area>910) throw new AllException("Ошибка, площвдь квартиры не может превышать 910 м^2");
        this.numberOfRooms = numberOfRooms; if (numberOfRooms<1) throw new AllException("Ошибка, в квартире должна быть хотя бы одна комната");
        this.furnish = furnish; if (furnish==null) throw new AllException("Поле не может быть null");
        this.view = view; if (view==null) throw new AllException("Поле не может быть null");
        this.transport = transport; if (transport==null) throw new AllException("Поле не может быть null");
        this.house = house;
        this.creationDate = java.util.Date.from(Instant.now());; if (creationDate==null) throw new AllException("Поле не может быть null");



    }

    public Flat()  throws IOException, AllException {

    }
    FileWorkPerson fileParse = new FileWorkPerson();

    public Flat(Object flat) throws IOException {
    }

    public long randomId(){
        long id = (long) (Math.random()*(1000+1));
        return id;
    }


    public static void FileSaving() throws IOException, AllException {

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Введите имя нового файла");
        Scanner scan = new Scanner(System.in);

        String pathToFile = scan.nextLine();
        File file = new File(pathToFile);
        System.out.println("Коллекция успешно сохранена в файл");
        try {
            mapper.writeValue(file, CollectionOfFlats.collection);
        } catch(IOException e ) {
            e.printStackTrace();

        }
    }


    public String getName() {
        return name;
    }
    public void setName (String name){
        this.name = name;
    }
    public  long getId() {return id;}
    public void setNumberOfRooms(Long numberOfRooms) {this.numberOfRooms = numberOfRooms;}
    public long getNumberOfRooms() {return numberOfRooms;}
    public void setId(long id) {this.id=id;}
    public java.util.Date getCreationDate() {return creationDate;}
    public void setCreationDate(java.util.Date creationDate) {this.creationDate=creationDate;}
    public Double getArea() {return area;};
    public void setArea(Double area) {this.area=area;}

    public Furnish getFurnish(){
        return furnish;
    }
    //public String getFurnish1() { return String.valueOf(furnish);}
    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }
    public Transport getTransport(){ return transport; }
    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public View getView(){
        return view;
    }
    public void setView(View view) { this.view = view; }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {this.coordinates=coordinates;}
    public House getHouse(){ return house; }
    public String getHouseName() {return house.getHouseName();}
    public Integer getHouseAge() {return house.getYear();}
    public Integer getLifts() {return house.getNumberOfLifts();}
    public void setHouse(House house) {this.house = house;}
    public void setLogin(String l){
        login = l;
    }
    public void setLogin() {login = Receiver.login;}
    public String getLogin(){
        return login;
    }
    public void setNewd(int ix){
        id =ix;
    }
    public void setNewId(){
        id = (int)(Math.random()*(1000+1));
    }

    public void  setFurnish(String furnish1){
        switch (furnish1){
            case("designer"):
                furnish= Furnish.designer;
                break;
            case("fine"):
                furnish = Furnish.fine;
                break;
            case("bad"):
                furnish = Furnish.bad;
                break;
            case("little"):
                furnish = Furnish.little;
                break;

        }

    }
    public void setView(String view1){

        switch (view1){
            case("STREET"):
                view= View.STREET;
                break;
            case("PARK"):
                view= View.PARK;
                break;
            case("NORMAL"):
                view= View.NORMAL;
                break;


        }

    }
    public void setTransport(String transport1){

        switch (transport1){
            case("FEW"):
                transport = Transport.FEW;
                break;
            case("ENOUGH"):
                transport = Transport.ENOUGH;
                break;
            case("NONE"):
                transport = Transport.NONE;
                break;


        }


    }
    public void setCreationDate(){
        creationDate = java.util.Date.from(Instant.now());
    }
    public void setHouseType(String namerere, Integer g, Integer h ) {
        this.house = new House();
        house.setName(namerere);
        house.setYear(g);
        house.setNumberOfLifts(h);


    }
    public Double getX(){
        return coordinates.getX();
    }
    public Double getY(){
        return coordinates.getY();
    }
    public void setCoordinates(Double xn, Double yn){
        this.coordinates = new Coordinates();
        coordinates.setXY(xn, yn);
    }

    @Override
    public String toString() {
        return (  " Имя: " + name + "\n" +
                "Площадь: " + area + "\n" + "Число комнат: " + numberOfRooms + "\n" + "Отделка: " + furnish + "\n" + "Вид " + view +  "\n"+
                "id: " + id + "\n" + "транспорт: "+ transport + "\n"+
                "Координаты: " + coordinates + "\n" + "" + house + "\n");
    }
    public long randomID(){
        this.id = (long) (Math.random()*(1000+1));
        return this.id;
    }
    public String DataForm() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }



    public void removeById() throws IOException, AllException {
        long idScanner = -20000;
        int k = 0;
        System.out.println("Введите id элемента (тип Long), чтобы удалить элемент с этим id");
        try {
            Scanner scan = new Scanner(System.in);
            while ((idScanner<=0)||(idScanner>1000)) {
                System.out.println("Не хулиганьте! Помните, что id должен быть положительным и не может превышать 1000");
                idScanner = scan.nextLong();
            }
            Iterator<Flat> itr = CollectionOfFlats.collection.iterator();
            while(itr.hasNext()){
                Flat flat = itr.next();
                if(flat.id == idScanner){
                    CollectionOfFlats.collection.remove(flat);
                    System.out.println("Элемент коллекции был успешно удален.");
                    k = k+1;
                } }
            if (k == 0){
                System.out.println("Элемента с введенным вами id в коллекции нет.");
            }} catch (InputMismatchException e) {
            System.out.println("Ошибка ввода, повторите");
            removeById();
        }
    }



    public void countByRooms()  {
        long roomsScanner = -30;
        int k = 0;
        long r =0;
        System.out.println("Введите количество комнат, чтобы посчитать сколько квартир в коллекции имеют столько комнат.");
        try {
            Scanner scan = new Scanner(System.in);
            while ((roomsScanner<=0)||(roomsScanner>500)) {
                System.out.println("Не хулиганьте! Помните, что в квартире должна быть хотя бы 1 комната, но и невероятно много их быть не может, возьмем за максимум 500 комнат");
                roomsScanner = scan.nextLong(); }
            Iterator<Flat> itr = CollectionOfFlats.collection.iterator();
            while(itr.hasNext()){
                Flat flat = itr.next();
                if(flat.numberOfRooms == roomsScanner){
                    r = k+1;
                    k = k+1;
                }
            }
            if (k == 0)
                System.out.println("Элемента с введенным вами количеством комнат в коллекции нет.");
            else System.out.println("В коллекции есть "+ r + " квартир, в которых число комнат равно " + roomsScanner);}
        catch (InputMismatchException e) {
            System.out.println("Ошибка ввода, повторите");
            countByRooms();
        }
    }
    public void removeByHouseAge() throws IOException, AllException {
        Integer idScanner = -10;
        int k = 0;
        System.out.println("Введите возраст дома, чтобы удалить все элементы коллекции с этим значением возраста дома.");
        try {
            Scanner scan = new Scanner(System.in);
            while ((idScanner<=0)||(idScanner>300)) {
                System.out.println("Не хулиганьте! Помните, что возраст дома должен быть положительным и не может превышать 300");
                idScanner = scan.nextInt();}
            Iterator<Flat> itr2 = CollectionOfFlats.collection.iterator();
            while (itr2.hasNext()) {
                Flat flat = itr2.next();
                if (flat.house.year == idScanner) {
                    itr2.remove();
                    k = k + 1;
                }
            }
            if(k!=0) System.out.println("Элементы коллекции были успешно удалены.");
            else System.out.println("Элементов с введенным возрастом дома нет");} catch (InputMismatchException e) {
            System.out.println("Ошибка ввода, повторите");
            removeByHouseAge();
        }

    }
    public void stringFishing(){
        String nameScanner = "hh" ;
        int k = 0;
        System.out.println("Введите пять символов; На экран будут выведены квартиры, названия которых начинаются с этих символов");
        try {

            Scanner scan = new Scanner(System.in);
            while (nameScanner.length()!=5) {
                System.out.println("Строка должна быть длиной в пять символов!");
                nameScanner = scan.nextLine();
            }
            Iterator<Flat> itr2 = CollectionOfFlats.collection.iterator();
            while (itr2.hasNext()) {
                Flat flat = itr2.next();
                String s = flat.name;
                String n = s.substring(0, 5);
                if (n.equals(nameScanner)) {
                    System.out.println(flat);
                    k=k+1;
                }
            }
            if (k == 0) System.out.println("В коллекции нет элемента, имя которого начинается с введенной вами строки");}
        catch (InputMismatchException e ){
            System.out.println("Ошибка ввода, повторите");
            stringFishing();
        }
    }
    public void compare()  {
        long idScanner = -10;
        int k = 0;
        int t = CollectionOfFlats.collection.size();
        int t1 = CollectionOfFlats.collection.size();
        System.out.println("Введите id элемента (тип Long), чтобы удалить из коллекции элементы, превышающие заданный");
        try {

            Scanner scan = new Scanner(System.in);
            while ((idScanner<=0)||(idScanner>1000)) {
                System.out.println("Не хулиганьте! Помните, что id должен быть положительным и не может превышать 1000");
                idScanner = scan.nextLong();}
            Iterator<Flat> itr = CollectionOfFlats.collection.iterator();
            while(itr.hasNext()){
                Flat flat = itr.next();
                if(flat.id == idScanner){
                    k = k+1;
                }
            }
            if (k>0) {
                Iterator<Flat> itr1 = CollectionOfFlats.collection.iterator();
                while(itr1.hasNext()){
                    Flat flat2 = itr1.next();
                    if(flat2.id > idScanner){
                        itr1.remove();
                        t= t-1;
                    }
                }
                if (t1!=t) System.out.println("Коллекция была обновлена."); else System.out.println("В коллекции нет элемента, превышающего введенный вами.");
            }
            else System.out.println("В коллекции нет элемента с таким id.");} catch (InputMismatchException e){
            System.out.println("Ошибка ввода, повторите");
            compare();}

    }
    public void compare2()  {
        long idScanner = -10;
        int k = 0;
        int t = CollectionOfFlats.collection.size();
        int t1 = CollectionOfFlats.collection.size();
        System.out.println("Введите id элемента (тип Long), чтобы удалить из коллекции элементы, меньшие заданного");
        try {
            Scanner scan = new Scanner(System.in);
            while ((idScanner<=0)||(idScanner>1000)) {
                System.out.println("Не хулиганьте! Помните, что id должен быть положительным и не может превышать 1000");
                idScanner = scan.nextLong();}
            Iterator<Flat> itr = CollectionOfFlats.collection.iterator();
            while (itr.hasNext()) {
                Flat flat = itr.next();
                if (flat.id == idScanner) {
                    k = k + 1;
                }
            }
            if (k > 0) {
                Iterator<Flat> itr1 = CollectionOfFlats.collection.iterator();
                while (itr1.hasNext()) {
                    Flat flat2 = itr1.next();
                    if (flat2.id < idScanner) {
                        itr1.remove();
                        t = t - 1;
                    }
                }
                if (t1 != t) System.out.println("Коллекция была обновлена.");
                else System.out.println("В коллекции нет элемента, который меньше введенного вами.");
            } else System.out.println("В коллекции нет элемента с таким id.");
        } catch(InputMismatchException e) {
            System.out.println("Ошибка ввода, повторите");
            compare2();
        }
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
