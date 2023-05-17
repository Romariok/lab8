package server;


import DataStructure.CollectionManager;
import server.Connections.Connection;


public class ServerMain {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        CollectionManager manager = new CollectionManager();
        Connection connection = new Connection(port, manager);
        connection.start();
    }
}
