package infra;

import utils.DataClass;
import utils.IDataUpdate;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static utils.Utils.getFixedPort;


public class Server {

    private static IDataUpdate dataClass = new DataClass();

    public static void main(String args[]) {

        ServerSocket listenSocket = null;
        int serverPort = getFixedPort();

        try {
            System.out.println("::: Initializating the server!");

            listenSocket = new ServerSocket(serverPort);
            System.out.println("::: Server initialized and listen the port TCP/" + serverPort + "!");

            while (true) {
                Socket clientSocket = listenSocket.accept();

                System.out.println("lastRoom" + dataClass);

                Thread t = new Thread(new Connection(clientSocket, dataClass));
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Error on server listener : " + e.getMessage());
        } finally {
            if (listenSocket != null)
                try {
                    listenSocket.close();
                    System.out.println("Close connection in server, opening the port TCP/" + serverPort + "!");
                } catch (IOException e) {
                    System.out.println("Error on server close connection : " + e.getMessage());
                }
        }
    }
}