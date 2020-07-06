package CommandsProcessing;


import AnswerSender.CommandToObjectServer;
import AnswerSender.ServerAnswerNew;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayDeque;
import java.security.NoSuchAlgorithmException;
import PersonData.*;
import Auth.Authorization;
import Auth.ComplicatedObject;
public class Receiver implements Runnable {
   // public static final String URL = "jdbc:postgresql://localhost:1337/PersonLists";
   // public static final String USERNAME = "postgres";
   // public static final String PASSWORD = "abcd05200";
    public static final String URL = "jdbc:postgresql://pg:5432/studs";
    public static final String USERNAME = "s286551";
    public static final String PASSWORD = "ppi439";
    public static SocketChannel socketChannel = null;
    public static String[] sarray;
    public static Flat flat;
    public static long g;
    public static Long j;
    public static long p;
    public static String historyR;
    public static String s;
    public static File filename;
    public static volatile String login;
    public static volatile String locale;
    public String password;
    public String ScriptPassword;
    public static ArrayDeque<String> logins ;
    Thread thread;
    public Receiver()  {
        thread=new Thread(this, "Поток сервера с чтением запросов");
        thread.start(); //запускаем поток

    }

    @Override
    public void run() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(7070));
            while (true) {
                try {
                    socketChannel = serverSocketChannel.accept();
                    ObjectInputStream is = new ObjectInputStream(socketChannel.socket().getInputStream());
                    try {
                        while (socketChannel.isConnected()) {
                            s = "";
                            Object object = is.readObject();
                            if (object instanceof Authorization) {
                                login = ((Authorization) object).getLogin();
                                password = ((Authorization) object).getPassword();
                                ScriptPassword = encryptThisString(password);
                                userWork();
                            } else {
                                login = ((ComplicatedObject) object).getLogin() ;
                                g = ((ComplicatedObject) object).getId();
                                j = ((ComplicatedObject) object).getParam();
                                flat = ((ComplicatedObject) object).getFlat();
                                p = ((ComplicatedObject) object).getP();
                                historyR = ((ComplicatedObject) object).getHistory();
                                s = ((ComplicatedObject) object).getCommand();
                                locale = ((ComplicatedObject) object).getLocale();
                                filename = ((ComplicatedObject) object).getFileName();
                                //ExecutorService service = Executors.newFixedThreadPool(5);
                               Control con = new Control();
                               con.processing();
                            }
                        }
                    } catch (EOFException e) {
                        socketChannel.close();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {}
            }
        }catch (IOException e){}
    }
    public void userWork() throws IOException {
        String answer = "";
        String query = "SELECT * from users;";
        String query1 = "INSERT INTO users (login, password) VALUES (?,?)";
        boolean checker = false;
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD)){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("login").equals(login)) {
                    checker = true;
                    if (resultSet.getString("password").equals(ScriptPassword)) {
                        answer = "Вы успешно вошли в учётную запись. ";
                        CommandToObjectServer commandToObjectServer = new CommandToObjectServer(answer);
                        ServerAnswerNew.commandToObjectServers.addLast(commandToObjectServer);
                        new ServerAnswerNew();
                    }else{
                        answer = "00010010";
                        CommandToObjectServer commandToObjectServer = new CommandToObjectServer(answer);
                        ServerAnswerNew.commandToObjectServers.addLast(commandToObjectServer);
                        new ServerAnswerNew();
                        socketChannel.close();
                        new Receiver();
                    }
                }
            }
            if (!checker) {
                PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                preparedStatement1.setString(1,login);
                preparedStatement1.setString(2, ScriptPassword);
                preparedStatement1.executeUpdate();
                answer = "Пользователь успешно авторизовался.\n";
                CommandToObjectServer commandToObjectServer = new CommandToObjectServer(answer);
                ServerAnswerNew.commandToObjectServers.addLast(commandToObjectServer);
                new ServerAnswerNew();
            }
            logins=new ArrayDeque<>();
            logins.addFirst(login);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static String encryptThisString(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD2");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}
/*public static void FileSaving() throws IOException, AllException {

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Введите имя нового файла (Без указания типа файла)");
        Scanner scan = new Scanner(System.in);

        String pathToFile = scan.nextLine();
        File file = new File(pathToFile + ".json");
        System.out.println("Коллекция успешно сохранена в файл");
        try {
            mapper.writeValue(file, CollectionOfFlats.collection);
        } catch(IOException e ) {
            e.printStackTrace();

        }
    }*/