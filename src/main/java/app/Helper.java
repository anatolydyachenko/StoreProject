package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Helper {
    private final static Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static String mapToJson(Map<String, List> list) {
        if (list == null) {
            list = Collections.emptyMap();
        }
        return gson.toJson(list);
    }

    public static String listToJson(List list) {
        if (list == null) {
            list = Collections.emptyList();
        }
        return gson.toJson(list);
    }
}
