package communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Encoder {
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static String encode(Object obj) {
        return gson.toJson(obj);
    }
    public static <T> T decode(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }
}
