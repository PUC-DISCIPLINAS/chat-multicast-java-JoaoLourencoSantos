package utils;

public enum ActionEnum {
    MENU("0", ""),
    LIST_ALL_ROOMS("1", ""),
    LIST_ALL_USERS("2", ""),
    JOIN_ROOM("3", ""),
    CREATE_ROOM("4", ""),
    EXIT_ROOM("5", "");


    private String id;
    private String description;

    ActionEnum(String id, String description) {
        this.id = id;
        this.description = description;
    }
}
