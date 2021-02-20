package infra;

import utils.IDataUpdate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static utils.Utils.handleMessage;

public class Connection implements Runnable {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    IDataUpdate data;

    public Connection(Socket aClientSocket, IDataUpdate data) {
        try {

            this.data = data;

            System.out.println("Oppening connection with client!");
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error on create connection socket: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String data = in.readUTF();
            System.out.println("Reading data: " + data);

            out.writeUTF(handleMessage(data, this.data));
            System.out.println("Sending data! Option : " + data);
        } catch (IOException e) {
            System.out.println("Error in connection with client : " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Close connection with client!");
            } catch (IOException e) {
                System.out.println("Error on opening connection socket: " + e.getMessage());
            }
        }

    }

}
