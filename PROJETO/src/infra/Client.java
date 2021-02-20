package infra;

import utils.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import static utils.Utils.getFixedPort;

public class Client {
    public static final Integer M_PORT = 6789;
    public static final String HOST = "localhost";
    public static Scanner sc = new Scanner(System.in);

    public static DataOutputStream out;
    public static DataInputStream in;

    public static void main(String args[]) {

        Socket s = null;
        MulticastSocket multicastSocket = null;
        int serverPort = getFixedPort();

        try {

            s = new Socket(HOST, serverPort);
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());

            String message = args[0];
            System.out.println("Enviando dados: " + message);
            out.writeUTF(message);

            String receivedData = in.readUTF();

            if (args[0].toUpperCase().contains("MENU") && !receivedData.isEmpty()) {
                System.out.println(receivedData);
            }

            if (args[0].toUpperCase().contains("LIST_ALL_ROOMS") && !receivedData.isEmpty()) {
                System.out.println(receivedData);
            }

            if (args[0].toUpperCase().contains("LIST_ALL_USERS") && !receivedData.isEmpty()) {
                System.out.println(receivedData);
            }

            if (args[0].toUpperCase().contains("JOIN_ROOM") && !receivedData.isEmpty()) {

                if (receivedData.equalsIgnoreCase("SUCCESS")) {
                    String[] listMessage = Utils.getMessage(args[0]);

                    String roomId = listMessage[1].trim();
                    String userName = listMessage[2].trim();

                    joinRoom(multicastSocket, roomId, userName);
                }
            }

            if (args[0].toUpperCase().contains("CREATE_ROOM") && !receivedData.isEmpty()) {

                String[] listMessage = Utils.getMessage(args[0]);


                String userName = listMessage.length > 1 ? listMessage[1] : "Usuário não identificado";
                createRoom(multicastSocket, receivedData, userName);
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


    private static void createRoom(MulticastSocket multicastSocket, String groupId, String userName) throws IOException {
        InetAddress groupIp = InetAddress.getByName(groupId);

        multicastSocket = new MulticastSocket(M_PORT);
        multicastSocket.joinGroup(groupIp);

        Thread t = new Thread(new Multicast(multicastSocket, groupIp, M_PORT));
        t.start();

        initMesseger(multicastSocket, groupIp, userName);

    }

    private static void joinRoom(MulticastSocket multicastSocket, String groutId, String userName) throws IOException {

        try {
            InetAddress groupIp = InetAddress.getByName(groutId);

            multicastSocket = new MulticastSocket(M_PORT);
            multicastSocket.joinGroup(groupIp);

            initMesseger(multicastSocket, groupIp, userName);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (multicastSocket != null)
                multicastSocket.close();
        }
    }

    private static void initMesseger(MulticastSocket multicastSocket, InetAddress groupIp, String userName) throws IOException {

        while (true) {
            String message;
            message = sc.nextLine();

            if (message.equalsIgnoreCase("Exit")) {

                Socket s = new Socket(HOST, getFixedPort());
                in = new DataInputStream(s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());

                System.out.println("Removendo " + userName + " do grupo de mensagens : " + groupIp.toString().split("/")[1]);

                out.writeUTF("EXIT_ROOM // " + groupIp.toString().split("/")[1] + " // " + userName);

                String receivedData = in.readUTF();

                System.out.println(receivedData);

                multicastSocket.leaveGroup(groupIp);
                multicastSocket.close();
                break;
            }


            System.out.println(groupIp.toString() + " - " + userName + " - " + M_PORT);

            message = userName + ": " + message;
            byte[] buffer = message.getBytes();
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, groupIp, M_PORT);
            multicastSocket.send(datagram);
        }
    }
}
