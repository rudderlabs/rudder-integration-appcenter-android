package com.rudderstack.android.integrations.appcenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    // returns the eventMap of the given priority from the eventsArray supplied by the destination config
    static Map<String, Integer> getEventMap(@NonNull JsonArray eventsArray) {
        Map<String, Integer> eventMap = new HashMap<>();
        for (int i = 0; i < eventsArray.size(); i++) {
            JsonObject eventObject = (JsonObject) eventsArray.get(i);
            String eventName = eventObject.get("from").getAsString();
            String priority = eventObject.get("to").getAsString();
            if (priority.equals("Normal")) {
                eventMap.put(eventName, 0x01);
                continue;
            }
            eventMap.put(eventName, 0x02);
        }
        return eventMap;
    }

    // used to remove key-value pairs whose value is not a string type from the eventProperties map
    static Map<String, String> filterEventProperties(@Nullable Map<String, Object> eventProperties) {
        if (eventProperties == null) {
            return null;
        }
        Map<String, String> filteredEventProperties = new HashMap<>();
        for (Map.Entry<String, Object> entry : eventProperties.entrySet()) {
            if (entry.getValue() instanceof String || entry.getValue() instanceof Number) {
                filteredEventProperties.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return filteredEventProperties;
    }

}
