package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataClass implements IDataUpdate {

    private static Integer lastRoom = 0;
    private static List<String> rooms = new ArrayList<>();
    private static Map<String, List<String>> users = new HashMap();


    @Override
    public void updateLastRomm() {
        lastRoom += 1;
    }

    @Override
    public void updateRooms(String lastRoom) {
        rooms.add(lastRoom);
    }

    @Override
    public List<String> getRooms() {
        return rooms;
    }

    @Override
    public Integer getLastRoom() {
        return lastRoom;
    }

    @Override
    public void addUserInRoom(String room, String user) {
        if (users.get(room) == null || users.get(room).isEmpty()) {

            List<String> values = new ArrayList<>();
            values.add(user);

            users.put(room, values);

            return;
        }

        users.get(room).add(user);
    }

    @Override
    public String getUsersInRoom(String room) {
        return Utils.listUsersInRoom(users, room);
    }
}
