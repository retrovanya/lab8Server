package CommandsProcessing;

import PersonData.*;
import org.postgresql.util.PSQLException;
import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.Date;

import static Auth.ReceiveDataFromServer.locker;

public class Commands {
static Flat f;

    static {
        try {
            f = new Flat();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AllException e) {
            e.printStackTrace();
        }
    }

    public static Scanner slmmsk;
    private static ArrayList al = new ArrayList();
    private static int q=0;
    public static String myTime = "";
    public Commands()  {
    }

    public static String help(){
        String help = "help - справка по командам" +"\n" + "info - информация о колекции" + "\n" + "show - вывести все элементы коллекции" +
                "\n" + "add - добавить новый элемент"+"\n"+"updateById - обновить id элемента"+"\n"+"removeById - удалить элемент по id" +
                "\n"+"clear - очистить коллекцию " + "\n"+"script - скрипт"+"\n"+
                "exit - завершить программу без сохранения в файл" + "\n" + "removeGreater - удалить все эелементы, превышающие заданный"+"\n"+
                "removeLower - удалить все элементы, которые меньше заданного"+"\n"+ "history - вывести список последних команд" + "\n"+
                "removeByHouseAge - удалить элементы, у которых значения поля house эквивалентны заданному"+"\n"+
                "countByRooms - вывести количество элементов, значение которых равно заданному"+"\n";
        return help;

    }


    static String info(){
        Instant instant = Instant.now();
        ZoneId rus = ZoneId.of( "Europe/Moscow" );
        ZoneId nzl = ZoneId.of("NZ");
        ZoneId sw = ZoneId.of("Europe/Stockholm");
        ZoneId rom = ZoneId.of("Europe/Bucharest");
        ZonedDateTime zdt ;
        String info = "";
        switch (Receiver.locale){
            case ("Portugal"):
                zdt = ZonedDateTime.ofInstant(instant, nzl);
                myTime = zdt.toString();
                myTime = myTime.replaceAll("T","    ");
                break;
            case ("Svenska"):
                zdt = ZonedDateTime.ofInstant(instant, sw);
                myTime = zdt.toString();
                myTime = myTime.replaceAll("T","    ");
                break;
            case ("Русский"):
                zdt = ZonedDateTime.ofInstant(instant, rus);
                myTime = zdt.toString();
                myTime = myTime.replaceAll("T","    ");
                break;
            case ("Română"):
                zdt = ZonedDateTime.ofInstant(instant, rom);
                myTime = zdt.toString();
                myTime = myTime.replaceAll("T","    ");
                break;
        }
        if (CollectionOfFlats.collection.size() != 0){
            info = "Размер коллекции: " + CollectionOfFlats.collection.size() + "\n"
                    + "Тип коллекции: " + CollectionOfFlats.collection.getClass() + "\n"
                    + "Дата инициализации: " + myTime + "\n";
        }
        else  info = "Коллекция пуста";
        return info;
    }
    public static String showUsers() {
        String show = "";
        String sql = "SELECT * FROM users";
        try (Connection connection = DriverManager.getConnection(Receiver.URL,Receiver.USERNAME,Receiver.PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                show += "\n" +
                        resultSet.getString("login") ;
            }
        } catch (SQLException e) {
            System.out.println("возникла ошибка");
            e.printStackTrace();
        }
        return show;
    }


   /* public static String info(){
        String info;
        if (CollectionOfFlats.collection.size() != 0){
            info = "Размер коллекции: " + CollectionOfFlats.collection.size()+ "\n"
                    + "Тип коллекции: " + CollectionOfFlats.collection.getClass() + "\n"
                    + "Дата инициализации: " + Date.from(Instant.now()) ;
        }
        else  info = "Коллекция пуста";
        return info;
    }*/
    public static void loadDB(){
        CollectionOfFlats.collection.clear();
        String sql = "SELECT * FROM PersonLists";
        try (Connection connection = DriverManager.getConnection(Receiver.URL,Receiver.USERNAME,Receiver.PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                long id = resultSet.getInt("id");
                java.util.Date creationDate = resultSet.getDate("creationDate");
                Double area = resultSet.getDouble("area");
                Long numberOfRooms = resultSet.getLong("rooms");
                String furnishData = resultSet.getString("furnish");
                String transportData = resultSet.getString("transport");
                String viewData = resultSet.getString("view");
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                String nameOfHouse = resultSet.getString("house_name");
                Integer ageOfHouse = resultSet.getInt("house_age");
                Integer liftsOfHouse = resultSet.getInt("house_lifts");
                String login = resultSet.getString("login");
                Flat flatflat = new Flat(name, id,  new Coordinates(x,y), area, numberOfRooms, setFurnish(furnishData), setView(viewData), setTransport(transportData),
                        new House(nameOfHouse, ageOfHouse, liftsOfHouse), login);
                CollectionOfFlats.collection.add(flatflat);
            }
        } catch (SQLException | AllException | IOException e) {
            System.out.println("возникла ошибка");
            e.printStackTrace();
        }
    }


    public static Furnish setFurnish(String furnish1){
        Furnish furnish = Furnish.designer;
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
        return furnish;
    }
    public static View setView(String view1){
        View view = View.PARK;
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
        return view;
    }
    public static Transport setTransport(String transport1){
        Transport transport = Transport.FEW;

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

        return transport;
    }



    public static String add(Flat flatat) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String answer = "";
        String sqlStatement = "INSERT INTO PersonLists ( creationDate,name, area,rooms,furnish,transport,view,x,y,house_name,house_age,house_lifts,login) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try  {
            connection = DriverManager.getConnection(Receiver.URL, Receiver.USERNAME, Receiver.PASSWORD);
            preparedStatement = connection.prepareStatement(sqlStatement);
            java.util.Date crDate = flatat.getCreationDate();
            java.sql.Date sqlDate = new java.sql.Date(crDate.getTime());
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setString(2, flatat.getName());
            preparedStatement.setDouble(3, flatat.getArea());
            preparedStatement.setLong(4, flatat.getNumberOfRooms());
            preparedStatement.setString(5, String.valueOf(flatat.getFurnish()));
            preparedStatement.setString(6, String.valueOf(flatat.getTransport()));
            preparedStatement.setString(7, String.valueOf(flatat.getView()));
            preparedStatement.setDouble(8, flatat.getCoordinates().getX());
            preparedStatement.setDouble(9, flatat.getCoordinates().getY());
            preparedStatement.setString(10, flatat.getHouse().getHouseName());
            preparedStatement.setInt(11, flatat.getHouse().getYear());
            preparedStatement.setLong(12, flatat.getHouse().getNumberOfLifts());
            preparedStatement.setString(13,Receiver.login);
            preparedStatement.executeUpdate();
            loadDB();
            answer = "Элемент успешно добавлен в коллекцию.";
            System.out.println(crDate.getTime());
        } catch (SQLException e) {
            e.printStackTrace();
            answer += "Возникла ошибка.";
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return answer;
    }


     static String updateId(long x, Flat flat) throws SQLException {
         System.out.println(x);
        locker.lock();
        java.util.Date crDate = flat.getCreationDate();
        java.sql.Date sqlDate = new java.sql.Date(crDate.getTime());
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String answer = "";
        String condition = "SELECT * FROM PersonLists WHERE login = " + "'" + Receiver.login + "'";
        int ix = (int) (Math.random() * (1000 + 1));
        try {
            connection = DriverManager.getConnection(Receiver.URL, Receiver.USERNAME, Receiver.PASSWORD);
            preparedStatement = connection.prepareStatement(condition, ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = preparedStatement.executeQuery();
            int counter = 0;
            while (resultSet.next()) {
                if((resultSet.getInt(1))== x) {
                    resultSet.updateString("name", flat.getName());
                    resultSet.updateDouble("area", flat.getArea());
                    resultSet.updateLong("rooms", flat.getNumberOfRooms());
                    resultSet.updateString("furnish", String.valueOf(flat.getFurnish()));
                    resultSet.updateString("transport", String.valueOf(flat.getTransport()));
                    resultSet.updateString("view", String.valueOf(flat.getView()));
                    resultSet.updateDate("creationdate", sqlDate);
                   // resultSet.updateInt("id", ix);
                    resultSet.updateString("house_name", flat.getHouse().getHouseName());
                    resultSet.updateInt("house_age", flat.getHouse().getYear());
                    resultSet.updateInt("house_lifts", flat.getHouse().getNumberOfLifts());
                    resultSet.updateDouble("x", flat.getCoordinates().getX());
                    resultSet.updateDouble("y", flat.getCoordinates().getY());
                    resultSet.updateRow();
                    counter++;
                }
            }
            if (counter == 0) {
                answer = "Элемент с таким id не найден или у пользователя (" + Receiver.login + ") нет доступа к модификации объекта c id " + x;
            } else {
                answer = "Значения элемента успешно обновлены.";
                CollectionOfFlats.collection.clear();
                loadDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            answer += "Возникла ошибка.";
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
            locker.unlock();
        }
        return answer;
    }


    public static String removeById(long x) throws SQLException {
        System.out.println(x);
        locker.lock();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String answer ="";
        String select = "DELETE FROM PersonLists WHERE id = " + x + " AND login = " + "'" + Receiver.logins.getFirst() + "'" + ";";

        try  {
            connection = DriverManager.getConnection(Receiver.URL, Receiver.USERNAME, Receiver.PASSWORD);
            preparedStatement = connection.prepareStatement(select);
            int j = preparedStatement.executeUpdate();
            if (j==1){
                answer = "Элемент успешно удалён";
                CollectionOfFlats.collection.clear();
                loadDB();
            } else if (j == 0){
                System.out.println(x);
                answer = "Элемент с таким id не найден / пользователь с логином "+ Receiver.login + " не имеет доступа к элементу с id " + x;
            }else{
                answer = "Элемент успешно удалён";
                CollectionOfFlats.collection.clear();
                loadDB();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            locker.unlock();
        }
        return answer;

    }

    public static String show() {
        String show = "";
        String sql = "SELECT * FROM PersonLists";
        try (Connection connection = DriverManager.getConnection(Receiver.URL,Receiver.USERNAME,Receiver.PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                show += "\n" + resultSet.getString("name") + "\n" +
                        resultSet.getInt("id") + "\n" +
                        resultSet.getDate("creationDate") + "\n" +
                        resultSet.getLong("area") + "\n" +
                        resultSet.getLong("rooms") + "\n" +
                        resultSet.getString("furnish") + "\n" +
                        resultSet.getString("transport") + "\n" +
                        resultSet.getString("view") + "\n" +
                        resultSet.getFloat("x") + "\n" +
                        resultSet.getFloat("y") + "\n" +
                        resultSet.getString("house_name") + "\n" +
                        resultSet.getLong("house_age") +"\n" +
                        resultSet.getLong("house_lifts") +"\n" +
                        resultSet.getString("login");
            }
        } catch (SQLException e) {
            System.out.println("возникла ошибка");
            e.printStackTrace();
        }
        return show;
    }

    /*public static String show() throws IOException, AllException {
        String show;
        if (CollectionOfFlats.collection.size() != 0) {

            return String.valueOf(CollectionOfFlats.Collection());
        }
        else show = "Коллекция пуста";
        return show;
    }*/
    public static String showByName(String x){
        int k=0;
        Iterator<Flat> itr23 = CollectionOfFlats.collection.iterator();
        while (itr23.hasNext()) {
            Flat flat = itr23.next();
            String s = flat.getName();

            if (s.contains(x)) {
               k=k+1;
            }
        }

        if (k==0) return "В коллекции нет такой квартиры" + x; else {
            Iterator<Flat> itr232 = CollectionOfFlats.collection.iterator();
            while (itr232.hasNext()) {
                Flat flat = itr232.next();
                String s = flat.getName();

                if (s.contains(x)) {
                    return "Квартира " + flat;
                }
            }
        }
        return "";
    }


    public static String removeByHouseAge (long uu){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String answer ="";
        String select = "DELETE FROM PersonLists WHERE house_age = " + uu + " AND login = " + "'" + Receiver.logins.getFirst() + "'" + ";";
        try  {
            connection = DriverManager.getConnection(Receiver.URL, Receiver.USERNAME, Receiver.PASSWORD);
            preparedStatement = connection.prepareStatement(select);
            int j = preparedStatement.executeUpdate();
            if (j==1){
                answer = "Элементы, возраст дома которых равен " + uu+ " созданы пользователем с логином "+ Receiver.logins.getFirst() +", успешно удалены.";
            } else if (j == 0){
                answer = "Элементы, возраст дома которых равен " + uu + " не найдены.";
            }else{
                answer = "Элементы,  которых превышает " + uu + " созданы пользователем с логином "+ Receiver.logins.getFirst() +", успешно удалены.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CollectionOfFlats.collection.clear();
        loadDB();
        return answer;
    }


    public static String countByRooms(long x) {
        int k =0;
        long r=0;
        Iterator<Flat> itr = CollectionOfFlats.collection.iterator();
        while(itr.hasNext()){
            Flat flat = itr.next();
            if(flat.getNumberOfRooms()== x){
                r = k+1;
                k = k+1;
            }
        }
        if (k == 0)
          return  "Элемента с введенным вами количеством комнат в коллекции нет.";
        else return "В коллекции есть "+ r + " квартир, в которых число комнат равно " + x;}


     static String removeGreater (long uu){

        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String answer ="";
        long xj = uu;
        uu = uu+1;
        String select = "DELETE FROM PersonLists WHERE id > " + uu + " AND login = " + "'" + Receiver.logins.getFirst() + "'" + ";";
         System.out.println(Receiver.login);
        try  {
            connection = DriverManager.getConnection(Receiver.URL, Receiver.USERNAME, Receiver.PASSWORD);
            preparedStatement = connection.prepareStatement(select);
            int j = preparedStatement.executeUpdate();
            if (j==1){
                answer = "Элементы, id которых превышает " + uu+ " созданы пользователем с логином "+ Receiver.logins.getFirst() +", успешно удалены.";
            } else if (j == 0){
                answer = "Элементы, id которых превышает " + xj + " не найдены.";
            }else{
                answer = "Элементы, id которых превышает " + xj + " созданы пользователем с логином "+ Receiver.logins.getFirst() +", успешно удалены.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CollectionOfFlats.collection.clear();
        loadDB();
        return answer;
    }


    public static String removeLower (long uu){
        long xj = uu;
        uu = uu+1;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String answer ="";
        String select = "DELETE FROM PersonLists WHERE id < " + uu + " AND login = " + "'" + Receiver.logins.getFirst() + "'" + ";";
        try  {
            connection = DriverManager.getConnection(Receiver.URL, Receiver.USERNAME, Receiver.PASSWORD);
            preparedStatement = connection.prepareStatement(select);
            int j = preparedStatement.executeUpdate();
            if (j==1){
                answer = "Элементы, id которых меньше " + uu+ " созданы пользователем с логином "+ Receiver.logins.getFirst() +", успешно удалены.";
            } else if (j == 0){
                answer = "Элементы, id которых меньше " + xj + " не найдены.";
            }else{
                answer = "Элементы, id которых меньше " + xj + " созданы пользователем с логином "+ Receiver.logins.getFirst() +", успешно удалены.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CollectionOfFlats.collection.clear();
        loadDB();
        return answer;
    }


    private static String getFilePathForScript(){
        String path = System.getenv("duo");

        if (path == null){
            return ("----\nПуть через переменную окружения dos не указан\n----");
        } else {
            return path;
        }
    }
    public String stringFishing(String str){
        int k = 0;
            Iterator<Flat> itr2 = CollectionOfFlats.collection.iterator();
            while (itr2.hasNext()) {
                Flat flat = itr2.next();
                String s = flat.getName();
                String n = s.substring(0, 5);
                if (n.equals(str)) {
                    System.out.println(flat);
                    k=k+1;
                }
            }
            if (k == 0) return "В коллекции нет элемента, имя которого начинается с введенной вами строки";
            else return "Элементы найдены";
    }



    public static String clear() throws SQLException {
        String clear = "";
        try {
            Connection conn = DriverManager.getConnection(Receiver.URL, Receiver.USERNAME, Receiver.PASSWORD);
            String deleteTableSQL = "DELETE FROM PersonLists WHERE login = " + "'" + Receiver.login + "'" + ";";
            Statement statement = conn.createStatement();
            statement.execute(deleteTableSQL);
            CollectionOfFlats.collection.clear();
            loadDB();
            clear = "Коллекция успешно отформатирована. (Удалены все объекты, принадлежащие пользователю с логином " + Receiver.login + ")";
        }catch (PSQLException e){
            clear = "Данный пользователь "+ "(" + Receiver.login + ")" +" не может удалить элементы коллекции, т.к. они созданы другим пользователем. ";
        }
        return clear;
    }


    public  static ArrayList<String> hy() {
        int yuy =0;
        Scanner scanner = null;
        ArrayList<String> scriptoData = new ArrayList<>();
        try {



                Scanner scanInteractive = new Scanner(System.in);
                String userCommand = "";
                String[] finalUserCommand;

                FileReader fr = new FileReader("scriptFile.TXT");
                Scanner scanCloser = new Scanner(fr);


                while ((scanCloser.hasNextLine()) &&(yuy<5)) {

                    userCommand = scanCloser.nextLine();
                    //userCommand = userCommand.trim();
                    finalUserCommand = userCommand.trim().split(" ", 2);


                    switch (finalUserCommand[0]) {
                        case"help":
                            help();
                            scriptoData.add(help());
                            break;
                        case"info":
                            info();
                            scriptoData.add(info());

                            break;
                        case"clear":
                            CollectionOfFlats.collection.clear();
                            System.out.println("Все элементы коллекции удалены");

                            break;
                        case"show":
                            show();
                            scriptoData.add(show());

                            break;
                        case"add":
                            int t = CollectionOfFlats.collection.size();
                            Flat fl =new Flat();
                            fl.setName(scanCloser.nextLine());
                            Double num = Double.parseDouble(scanCloser.nextLine());
                            Double num2 = Double.parseDouble(scanCloser.nextLine());
                            fl.setCoordinates(new Coordinates(num, num2));
                            Double num3 = Double.parseDouble(scanCloser.nextLine());
                            fl.setArea(num3);
                            Long num4 = Long.parseLong(scanCloser.nextLine());
                            fl.setNumberOfRooms(num4);
                            String fur = scanCloser.nextLine();
                            if (fur.equals("Furnish.designer"))
                                fl.setFurnish(Furnish.designer);
                            if (fur.equals("Furnish.little"))
                                fl.setFurnish(Furnish.little);
                            if (fur.equals("Furnish.fine"))
                                fl.setFurnish(Furnish.fine);
                            if (fur.equals("Furnish.bad"))
                                fl.setFurnish(Furnish.bad);
                            String viewww = scanCloser.nextLine();
                            if (viewww.equals("View.PARK"))
                                fl.setView(View.PARK);
                            if (viewww.equals("View.STREET"))
                                fl.setView(View.STREET);
                            if (viewww.equals("View.NORMAL"))
                                fl.setView(View.NORMAL);
                            String transp = scanCloser.nextLine();
                            if (transp.equals("Transport.Few"))
                                fl.setTransport(Transport.FEW);
                            if (transp.equals("Transport.Enough"))
                                fl.setTransport(Transport.ENOUGH);
                            if (transp.equals("Transport.None"))
                                fl.setTransport(Transport.NONE);
                            House house11 =new House();
                            house11.name = scanCloser.nextLine();
                            Integer num5 = Integer.parseInt(scanCloser.nextLine());
                            Integer num6 = Integer.parseInt(scanCloser.nextLine());
                            house11.year =num5;
                            house11.numberOfLifts =num6;
                            fl.setHouse(house11);
                            fl.setId(fl.randomId());
                            CollectionOfFlats.collection.add(fl);
                            if (CollectionOfFlats.collection.size()!=t)
                                System.out.println("Квартира успешно добавлена в коллекцию.");
                            break;
                        case "removeById":
                            removeByIdScript(scanCloser.nextLine());

                            break;

                        case"updateId":
                            updateIdScript(scanCloser.nextLine());

                            break;
                        case "countByRooms":
                            countByRoomsScript(scanCloser.nextLine());

                            break;
                        case "removeByHouseAge":
                            removeByHouseAgeScript(scanCloser.nextLine());

                            break;
                        case "showByName":
                            stringFishingScript(scanCloser.nextLine());

                            break;
                        case "removeGreater":
                            compareScript(scanCloser.nextLine());

                            break;
                        case "removeLower":
                            compare2Script(scanCloser.nextLine());
                            break;
                        case "script":
                            yuy =yuy+1;
                            hy();
                            break;
                        case"exit":
                            System.exit(0);
                    }


                }
                fr.close();
             } catch (IOException e) {
            e.printStackTrace();
        } catch (AllException e) {
            e.printStackTrace();
        }return scriptoData;

    }

    public static void removeByIdScript(String str) {
        long idScanner = -20000;
        int k = 0;
        Long numID = Long.parseLong(str);
        idScanner = numID;

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
        }
    }
    public static void updateIdScript(String str)  {
        long idScanner = -200;
        int k = 0;
        Long numID = Long.parseLong(str);


        idScanner = numID;
        Iterator<Flat> itr = CollectionOfFlats.collection.iterator();
        while(itr.hasNext()){
            Flat flat = itr.next();
            if(flat.id == idScanner){
                flat.id = flat.randomId();
                System.out.println("id элемента был успешно обновлен.");
                k = k+1;
            }
        }
        if (k == 0){
            System.out.println("Элемента с введенным вами id в коллекции нет.");
        }
    }
    public static void countByRoomsScript(String str)  {
        long roomsScanner = -30;
        int k = 0;
        long r =0;
        Long numRooms = Long.parseLong(str);



        roomsScanner = numRooms;
        Iterator<Flat> itr = CollectionOfFlats.collection.iterator();
        while(itr.hasNext()){
            Flat flat = itr.next();
            if(flat.getNumberOfRooms() == roomsScanner){
                r = k+1;
                k = k+1;
            }
        }
        if (k == 0)
            System.out.println("Элемента с введенным вами количеством комнат в коллекции нет.");
        else System.out.println("В коллекции есть "+ r + " квартир, в которых число комнат равно " + roomsScanner);
    }
    public static void removeByHouseAgeScript(String str){
        Integer idScanner = -10;
        int k = 0;
        Integer numRooms = Integer.parseInt(str);



        idScanner = numRooms;
        Iterator<Flat> itr2 = CollectionOfFlats.collection.iterator();
        while (itr2.hasNext()) {
            Flat flat = itr2.next();
            if (flat.getHouse().year == idScanner) {
                itr2.remove();
                k = k + 1;
            }
        }
        if(k!=0) System.out.println("Элементы коллекции были успешно удалены.");
        else System.out.println("Элементов с введенным возрастом дома нет");
    }
    public static void stringFishingScript(String str){
        String nameScanner = "hh" ;
        int k = 0;





        nameScanner = str;

        Iterator<Flat> itr2 = CollectionOfFlats.collection.iterator();
        while (itr2.hasNext()) {
            Flat flat = itr2.next();
            String s = flat.getName();
            String n = s.substring(0, 5);
            if (n.equals(nameScanner)) {
                System.out.println(flat);
                k=k+1;
            }
        }
        if (k == 0) System.out.println("В коллекции нет элемента, имя которого начинается с введенной вами строки или введенная вами строка содержит меньше 5 символов");
    }
    public static void compareScript(String str)  {
        long idScanner = -10;
        Long numID = Long.parseLong(str);
        int k = 0;
        int t = CollectionOfFlats.collection.size();
        int t1 = CollectionOfFlats.collection.size();




        idScanner = numID;
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
        else System.out.println("В коллекции нет элемента с таким id.");

    }
    public static void compare2Script(String str)  {
        long idScanner = -10;
        int k = 0;
        Long numID = Long.parseLong(str);
        int t = CollectionOfFlats.collection.size();
        int t1 = CollectionOfFlats.collection.size();



        idScanner = numID;
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
    }
}