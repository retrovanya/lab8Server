package CommandsProcessing;

import AnswerSender.ServerAnswerNew;
import AnswerSender.CommandToObjectServer;
import java.io.IOException;
import java.sql.SQLException;
import static CommandsProcessing.Commands.*;
import static CommandsProcessing.Receiver.*;
//import static CommandsProcessing.Receiver.*;

public class Control {

    Commands comm = new Commands();
    public Control() {
    }
    public void processing() {
        try{
            sarray = s.trim().split(" ", 2);
            switch (sarray[0]) {
                case "help":
                    CommandToObjectServer help = new CommandToObjectServer("help", help());
                    ServerAnswerNew.commandToObjectServers.addLast(help);
                    new ServerAnswerNew();
                    break;
                case "users":
                    CommandToObjectServer users = new CommandToObjectServer("users", showUsers());
                    ServerAnswerNew.commandToObjectServers.addLast(users);
                    new ServerAnswerNew();
                    break;
                case "info":
                    CommandToObjectServer info = new CommandToObjectServer("info", info());
                    ServerAnswerNew.commandToObjectServers.addLast(info);
                    new ServerAnswerNew();
                    break;
                case "show":
                    CommandToObjectServer show = new CommandToObjectServer("show", show());
                    ServerAnswerNew.commandToObjectServers.addLast(show);
                    new ServerAnswerNew();
                    break;
                case "update_id":
                    System.out.println("f");
                    CommandToObjectServer updateId = new CommandToObjectServer("update_id", String.valueOf(comm.updateId(g, flat)));//эти переменные считываются программой как нулевые
                    ServerAnswerNew.commandToObjectServers.addLast(updateId);
                    new ServerAnswerNew();
                    break;
                case "remove_by_id":
                    CommandToObjectServer removeById = new CommandToObjectServer("remove_by_id", comm.removeById(g));
                    ServerAnswerNew.commandToObjectServers.addLast(removeById);
                    new ServerAnswerNew();
                    break;
                case "add":
                    CommandToObjectServer add = new CommandToObjectServer("add",add(flat));
                    ServerAnswerNew.commandToObjectServers.addLast(add);
                    new ServerAnswerNew();
                    break;
                case "remove_greater":
                    CommandToObjectServer removeGr = new CommandToObjectServer("removeGreater", comm.removeGreater(g));
                    ServerAnswerNew.commandToObjectServers.addLast(removeGr);
                    new ServerAnswerNew();
                    break;
                case "remove_lower":
                    CommandToObjectServer removeLow = new CommandToObjectServer("removeLower", comm.removeLower(g));
                    ServerAnswerNew.commandToObjectServers.addLast(removeLow);
                    new ServerAnswerNew();
                    break;
                case "countByRooms":
                    CommandToObjectServer cbr = new CommandToObjectServer("countByRooms", comm.countByRooms(g));
                    ServerAnswerNew.commandToObjectServers.addLast(cbr);
                    new ServerAnswerNew();
                    break;
                case "history":
                    CommandToObjectServer hist = new CommandToObjectServer("history", historyR);
                    ServerAnswerNew.commandToObjectServers.addLast(hist);
                    new ServerAnswerNew();
                    break;
                case "remove_by_house_age":
                    CommandToObjectServer rbha  = new CommandToObjectServer("remove_by_house_age", comm.removeByHouseAge(g));
                    ServerAnswerNew.commandToObjectServers.addLast(rbha);
                    new ServerAnswerNew();
                    break;
                case "clear":
                    CommandToObjectServer clear = new CommandToObjectServer("clear", clear());
                    ServerAnswerNew.commandToObjectServers.addLast(clear);
                    new ServerAnswerNew();
                    break;
            }
        }catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }
}