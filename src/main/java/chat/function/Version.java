package chat.function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Version {
    public static String prefix, type;
    public static float version;
    public static Gson gson;
    public static Long message_id = 0L;

    public Version(String prefix, String type, float version) {
        this.version = version;
        this.prefix = prefix;
        this.type = type;
        this.gson = new GsonBuilder().create();
    }

    public static Long getMessage_id() {
        message_id++;
        return message_id - 1;
    }
}
