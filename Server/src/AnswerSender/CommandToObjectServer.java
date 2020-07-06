package AnswerSender;

import PersonData.Flat;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashSet;

public class CommandToObjectServer implements Serializable {
    private String command;
    String answer;
    boolean checker;
    public CommandToObjectServer(String command, HashSet<Flat> collection){
        this.command = command;
        this.answer = answer;
    }
    public CommandToObjectServer(String command, ArrayDeque<Flat> collection){
        this.command = command;
        this.answer = answer;
    }
    public CommandToObjectServer(String command, String answer){
        this.command = command;
        this.answer = answer;
    }
    public CommandToObjectServer( String answer, boolean checker){
        this.answer = answer;
        this.checker = checker;
    }
    public CommandToObjectServer(String answer){
        this.answer = answer;
    }
    public String getCommand(){
        return command;
    }
    public String getAnswer(){
        return answer;
    }
}