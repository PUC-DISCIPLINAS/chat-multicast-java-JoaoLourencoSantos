package utils;

import java.util.List;

public interface IDataUpdate {

    void updateLastRomm();

    void updateRooms(String lastRoom);

    List<String> getRooms();

    Integer getLastRoom();
}
