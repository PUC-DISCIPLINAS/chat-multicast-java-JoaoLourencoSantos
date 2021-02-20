package utils;

import java.util.List;

public interface IDataUpdate {

    void updateLastRomm();

    void updateRooms(String lastRoom);

    List<String> getRooms();

    Integer getLastRoom();

    void addUserInRoom(String room, String user);

    String getUsersInRoom(String room);

    void removeUserFromRoom(String room, String userName);
}
