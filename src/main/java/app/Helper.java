package app;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Helper {
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String mapToJson(Map<String, Object> list) {
        if (list == null) {
            list = Collections.emptyMap();
        }
        return gson.toJson(list);
    }

    public static JsonObject parseToJsonObject(String jsonString) {
        JsonElement jsonelement = new JsonParser().parse(jsonString);
        return jsonelement.getAsJsonObject();
    }

    public static String getSql(String sqlName) {
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get(sqlName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String objectToJson(Object object) {
        if (object == null) {
            object = Collections.emptyMap();
        }
        return gson.toJson(object);
    }
}
