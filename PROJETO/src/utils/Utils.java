package utils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Utils {
    public static int getPort(String[] args) {
        if (args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {

            return 4845;
        }

        return Integer.parseInt(args[0]);
    }

    public static int getFixedPort() {
        return 4845;
    }

    public static String getRoom(int room) {
        return "228.5.6." + room;
    }

    public static String handleMessage(String clientMessage, IDataUpdate clientSocket) {
        System.out.println(clientMessage);
        if (clientMessage == null && clientMessage.isEmpty()) {
            return "Opps ... not is possible handle your message";
        }

        if (clientMessage.toUpperCase().equals("MENU")) {
            System.out.println("QAQUI");
            return menu();
        }


        if (clientMessage.toUpperCase().equals("LIST_ALL_ROOMS") || clientMessage.toUpperCase().equals(("1"))) {
            return listRooms(clientSocket.getRooms());
        }

        if (clientMessage.toUpperCase().equals("LIST_ALL_USERS") || clientMessage.toUpperCase().equals(("2"))) {
            return listRooms(clientSocket.getRooms());
        }

        if (clientMessage.toUpperCase().equals("SEND_MESSAGE") || clientMessage.toUpperCase().equals(("3"))) {
            return "Yes you choose the first option!";
        }

        if (clientMessage.toUpperCase().equals("JOIN_ROOM") || clientMessage.toUpperCase().equals(("4"))) {
            return "Yes you choose the first option!";
        }

        if (clientMessage.toUpperCase().equals("CREATE_ROOM") || clientMessage.toUpperCase().equals(("5"))) {
            clientSocket.updateLastRomm();
            Integer last = clientSocket.getLastRoom();

            System.out.println("Last: " + last);

            String room = getRoom(last);

            clientSocket.updateRooms(room);

            return room;
        }

        if (clientMessage.toUpperCase().equals("EXIT_ROOM") || clientMessage.toUpperCase().equals(("6"))) {
            return "Yes you choose the first option!";
        }

        return "Opps ... not was possible handle your message";
    }

    public static String menu() {
        return "\n::::::::::::: Hello my friend! Welcome to the BOT! This is your options, create connection passing the 1° param with option what you need :::::::::::::\n" +
                "::: 1 - List all Chat Room\n" +
                "::: 2 - List all user from Chat Room\n" +
                "::: 3 - Send message to the Chat Room\n" +
                "::: 4 - Join to the Chat Room\n" +
                "::: 5 - Create new Chat Room\n" +
                "::: 6 - Exit from Chat Room\n";
    }

    public static String listRooms(List<String> rooms) {

        if (rooms.isEmpty()) {
            return "\n::::::::::::: Not has any rooms";
        }

        return "\n::::::::::::: Rooms \n " + rooms.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("   ||  ", "{ ", " }"));
    }
}
