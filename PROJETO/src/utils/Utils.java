package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    public static String[] getMessage(String message) {
        return message.split("//"); // Para toda troca de mensagens com mais de um parametro é necessário passar com //
    }

    public static String handleMessage(String clientMessage, IDataUpdate clientSocket) {
        System.out.println("Received  => " + clientMessage);
        String[] messages = getMessage(clientMessage);
        System.out.println("Particional messages  => " + Arrays.asList(messages).toString());

        if (clientMessage == null && clientMessage.isEmpty()) {
            return "Opps ... not is possible handle your message";
        }

        if (clientMessage.toUpperCase().equals("MENU")) {
            return menu();
        }

        if (clientMessage.toUpperCase().equals("LIST_ALL_ROOMS")) {
            return listRooms(clientSocket.getRooms());
        }

        if (clientMessage.toUpperCase().contains("LIST_ALL_USERS")) {
            String room = messages.length > 1 ? messages[1] : "";
            return clientSocket.getUsersInRoom(room);
        }

        if (clientMessage.toUpperCase().contains("JOIN_ROOM")) {

            if (messages.length < 3) {
                return "ERROR_DATA";
            }

            String roomId = messages[1];
            String userName = messages[2];

            if (roomId == null || roomId.isEmpty() || userName == null || userName.isEmpty()) {
                return "ERROR_DATA";
            }

            roomId = messages[1].trim();
            userName = messages[2].trim();

            System.out.println("DATA : " + userName + " - " + roomId);

            clientSocket.addUserInRoom(roomId, userName);

            return "SUCCESS";
        }

        if (clientMessage.toUpperCase().contains("CREATE_ROOM")) {
            clientSocket.updateLastRomm();
            Integer last = clientSocket.getLastRoom();

            String room = getRoom(last);

            clientSocket.updateRooms(room);

            String userName = messages.length > 1 ? messages[1] : "Usuário não identificado";
            clientSocket.addUserInRoom(room.trim(), userName.trim());

            return room;
        }

        if (clientMessage.toUpperCase().contains("EXIT_ROOM")) {
            System.out.println("EXIT");


            String roomId = messages[1].trim();
            String userName = messages[2].trim();

            clientSocket.removeUserFromRoom(roomId, userName);

            try {
                return "Sucesso ao remover o usuário da sala !";
            } catch (Exception e) {
                return "Erro ao remover o usuário da sala!";
            }
        }

        return "Opps ... not was possible handle your message";
    }

    public static String menu() {
        return "\n::::::::::::: Olá meu amigo! Bem vindo ao CHATBOT! A baixo haverá suas opções, e no readme há mais detalhes sobre como utilizar  :::::::::::::\n" +
                "::: 1 - Listar as salas de chat\n" +
                "::: 2 - Listar os usuário de uma sala de chat ou de todas as salas\n" +
                "::: 3 - Enviar mensagens entre as salas\n" +
                "::: 4 - Se ajuntar à uma sala\n" +
                "::: 5 - Criar uma nova sala\n" +
                "::: 6 - Sair de uma sala\n";
    }

    public static String listRooms(List<String> rooms) {

        if (rooms.isEmpty()) {
            return "\n::::::::::::: Not has any rooms";
        }

        return "\n::::::::::::: Rooms \n " + rooms.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("   ||  ", "{ ", " }"));
    }

    public static String listUsersInRoom(Map<String, List<String>> users, String room) {

        String result = "";
        if (room != null && !room.isEmpty() && !room.trim().isEmpty() && users.get(room.trim()) != null) {

            return "\n::::::::::::: Room " + room + "\n" +
                    users.get(room.trim()).stream().map(String::valueOf)
                            .collect(Collectors.joining("   ||  ", "{ ", " }"));
        }

        result = "\n::::::::::::: Rooms \n";


        for (Map.Entry<String, List<String>> value : users.entrySet()) {
            result += value.getKey() + "  :: ";
            result += value.getValue().stream().map(String::valueOf)
                    .collect(Collectors.joining("   ||  ", "{ ", " }"));
            result += "\n";
        }

        return result;
    }
}
