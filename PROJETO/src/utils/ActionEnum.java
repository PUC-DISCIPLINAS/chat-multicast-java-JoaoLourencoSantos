package utils;

public enum ActionEnum {
    MENU("0", ""),
    LIST_ALL_ROOMS("1", ""),
    LIST_ALL_USERS("2", ""),
    SEND_MESSAGE("3", ""),
    JOIN_ROOM("4", ""),
    CREATE_ROOM("5", ""),
    EXIT_ROOM("6", "");


    private String id;
    private String description;

    ActionEnum(String id, String description) {
        this.id = id;
        this.description = description;
    }
}
