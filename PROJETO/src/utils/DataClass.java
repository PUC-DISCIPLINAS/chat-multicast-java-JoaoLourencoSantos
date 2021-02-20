package utils;

import java.util.ArrayList;
import java.util.List;

public class DataClass implements IDataUpdate {

    private static Integer lastRoom = 0;
    private static List<String> rooms = new ArrayList<>();
    private static List<String> users = new ArrayList<>();


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
}
