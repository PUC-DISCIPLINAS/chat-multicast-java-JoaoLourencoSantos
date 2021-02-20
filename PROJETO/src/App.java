import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        System.out.println(Utils.listUsersInRoom(getMapa(), ""));

    }

    public static Map<String, List<String>> getMapa() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("128.21.05.05", new ArrayList<String>() {
            {
                add("João");
                add("Marcelo");
                add("Pedro");
            }
        });

        map.put("128.21.05.08", new ArrayList<String>() {
            {
                add("João");
                add("Thiago");
                add("Pietro");
            }
        });
        return map;
    }
}
