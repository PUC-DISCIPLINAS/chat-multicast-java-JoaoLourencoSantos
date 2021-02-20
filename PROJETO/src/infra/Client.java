package infra;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static utils.Utils.getFixedPort;

public class Client {
    public static final Integer M_PORT = 6789;

    public static void main(String args[]) {

        Socket s = null;
        MulticastSocket mSocket = null;
        int serverPort = getFixedPort();

        try {
            s = new Socket(args[1], serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String message = args[0];
            System.out.println("Sending data: " + message);
            out.writeUTF(message);

            String receivedData = in.readUTF();

            if ((args[0].equals("0") || args[0].toUpperCase().equals("MENU")) && !receivedData.isEmpty()) {
                System.out.println(receivedData);
            }


            if ((args[0].equals("1") || args[0].toUpperCase().equals("LIST_ALL_ROOMS")) && !receivedData.isEmpty()) {
                System.out.println(receivedData);
            }

            if ((args[0].equals("4") || args[0].toUpperCase().equals("JOIN_ROOM")) && !receivedData.isEmpty()) {
                joinRoom(mSocket, receivedData);
            }

            if ((args[0].equals("5") || args[0].toUpperCase().equals("CREATE_ROOM")) && !receivedData.isEmpty()) {
                System.out.println(receivedData);
                //createRoom(mSocket, receivedData);
            }
            if ((args[0].equals("6") || args[0].toUpperCase().equals("EXIT_ROOM")) && !receivedData.isEmpty()) {
                leaveRoom(mSocket, receivedData);
            }

        } catch (UnknownHostException e) {
            System.out.println("Error on connect to server: " + e.getMessage());
        } catch (EOFException e) {
            System.out.println("Error on connect to server: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error on connect to server: " + e.getMessage());
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("Error on close connection with server: " + e.getMessage());
                }
        }
    }


    private static void createRoom(MulticastSocket mSocket, String received) throws IOException {
        System.out.println("Received data: " + received);
        InetAddress groupIp = InetAddress.getByName(received);

        System.out.println("Init multicast: " + received);
        mSocket = new MulticastSocket(M_PORT);
        mSocket.joinGroup(groupIp);

        Thread t = new Thread(new Multicast(mSocket, groupIp, M_PORT));
        t.start();

        mSocket.joinGroup(groupIp);
    }

    private static void joinRoom(MulticastSocket mSocket, String received) throws IOException {
        System.out.println("Received data: " + received);
        InetAddress groupIp = InetAddress.getByName(received);

        System.out.println("Init multicast: " + received);
        mSocket = new MulticastSocket(M_PORT);
        mSocket.joinGroup(groupIp);

        Thread t = new Thread(new Multicast(mSocket, groupIp, M_PORT));
        t.start();

        mSocket.joinGroup(groupIp);
    }

    private static void leaveRoom(MulticastSocket mSocket, String received) throws IOException {
        System.out.println("Received data: " + received);
        InetAddress groupIp = InetAddress.getByName(received);

        System.out.println("Init multicast: " + received);
        mSocket = new MulticastSocket(M_PORT);
        mSocket.joinGroup(groupIp);

        Thread t = new Thread(new Multicast(mSocket, groupIp, M_PORT));
        t.start();

        mSocket.joinGroup(groupIp);
    }
}
