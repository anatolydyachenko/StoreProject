package app;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Helper {
    private final static Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static String mapToJson(Map<String, Object> list) {
        if (list == null) {
            list = Collections.emptyMap();
        }
        return gson.toJson(list);
    }

    public static JsonObject parseToJsonObject (String jsonString){
        JsonElement jsonelement = new JsonParser().parse(jsonString);
        return jsonelement.getAsJsonObject();
    }

}
