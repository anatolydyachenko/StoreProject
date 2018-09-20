package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Helper {
    private final static Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public static String mapToJson(Map<?,?> list) {
        if (list == null) {
            list = Collections.emptyMap();
        }
        return gson.toJson(list);
    }
}
