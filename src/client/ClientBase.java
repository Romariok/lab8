package client;

import Auth.AuthResponse;
import Auth.Session;
import Command.CommandResponse;
import Command.CommandFactory;
import GUI.Frames.AuthFrame;

import java.awt.*;
import java.net.InetAddress;
import java.util.*;
import java.util.List;

import static client.ClientMain.arg;
import static Command.Serializer.serialize;
public class ClientBase implements Runnable {
    private final Connection connection;
    private Session session = new Session();

    public ClientBase(InetAddress address, int port) {
        try {
            connection = new Connection(address, port);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public void run() {
        EventQueue.invokeLater(() -> {
            AuthFrame ex = new AuthFrame(connection, session);
            ex.setVisible(true);
        });
        String commandName;
        String[] commandArgs;
        Scanner scanner = new Scanner(System.in);
        CommandFactory commandFactory = new CommandFactory();
        if (arg.length >= 3){
            if (Objects.equals(arg[1], "-exec")){
                try{
                    connection.send(serialize(new AuthResponse("execute_script",session.getUser(),session.isAuthorized(),arg[2],"")));
                    AuthResponse response = connection.recieve();
                    if (!response.getCommand().isEmpty()) System.out.println(response);
                    session.setUser(response.getUser());
                    session.setAuthoriazed(response.isAutorized());
                }
                catch (Exception ex){
                    System.err.println(ex.getMessage());
                }
            }
        }
        while (true) {
            List<String> input = Arrays.stream(scanner.nextLine().split(" ")).toList();
            commandName = input.get(0);
            commandArgs = input.subList(1, input.size()).toArray(new String[0]);

            if (commandName.equals("exit")) {
                System.out.println("Bye!");
                System.exit(1);
            }
            CommandResponse command = commandFactory.getCommand(commandName, commandArgs, scanner, false);
            AuthResponse sending;
            if (command != null){
                try{
                    if(command.getArgs() != null && command.getValue()!=null) {
                        sending = new AuthResponse(commandName, session.getUser(), session.isAuthorized(), command.getArgs()[0], command.getValue().toString());
                    } else if (command.getArgs() == null && command.getValue()!=null) {
                        sending = new AuthResponse(commandName, session.getUser(), session.isAuthorized(), "", command.getValue().toString());
                    } else if (command.getArgs() != null && command.getValue()==null) {
                        sending = new AuthResponse(commandName, session.getUser(), session.isAuthorized(), command.getArgs()[0], "");
                    }
                    else {
                        sending = new AuthResponse(commandName, session.getUser(), session.isAuthorized(), "", "");
                    }

                    connection.send(serialize(sending));
                    AuthResponse response = connection.recieve();
                    if (!response.getCommand().isEmpty()) System.out.println(response.getCommand());
                    session.setUser(response.getUser());
                    session.setAuthoriazed(response.isAutorized());
                }
                catch (Exception ex){
                    System.err.println(ex.getMessage());
                }

            } else {
                System.err.println("You just entered an unknown command! For list of available commands type 'help'");
            }
        }
    }
}
