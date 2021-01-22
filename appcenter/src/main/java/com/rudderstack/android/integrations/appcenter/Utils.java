package com.rudderstack.android.integrations.appcenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.appcenter.Flags;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    // returns the eventMap of the given priority from the eventsArray supplied by the destination config
    @NonNull
    static Map<String, Integer> getEventMap(@NonNull JsonArray eventsArray) {
        Map<String, Integer> eventMap = new HashMap<>();
        for (int i = 0; i < eventsArray.size(); i++) {
            JsonObject eventObject = (JsonObject) eventsArray.get(i);
            String eventName = eventObject.get("from").getAsString();
            String priority = eventObject.get("to").getAsString();
            if (priority.equalsIgnoreCase("normal")) {
                eventMap.put(eventName, Flags.NORMAL);
            } else {
                // Consider it CRITICAL by default
                eventMap.put(eventName, Flags.CRITICAL);
            }
        }
        return eventMap;
    }

    // used to remove key-value pairs whose value is not a string type from the eventProperties map
    @Nullable
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
