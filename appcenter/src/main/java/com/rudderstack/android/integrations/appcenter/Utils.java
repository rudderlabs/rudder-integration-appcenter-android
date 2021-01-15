package com.rudderstack.android.integrations.appcenter;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utils {

    static Set<String> getEventSet(@Nullable List<Map<String, String>> eventsList, String priority) {
        Set<String> eventSet = new HashSet<>();
        if (eventsList != null) {
            for (int i = 0; i < eventsList.size(); i++) {
                Map<String, String> eventsMap = eventsList.get(i);
                String eventName = eventsMap.get("from");
                String priorityFromMap = eventsMap.get("to");
                if (priorityFromMap.equals(priority)) {
                    eventSet.add(eventName);
                }
            }
        }
        return eventSet;
    }

    static Map<String, String> filterEventProperties(Map<String, Object> eventProperties) {
        Map<String, String> filteredEventProperties = new HashMap<>();
        if (eventProperties != null) {
            for (Map.Entry<String, Object> entry : eventProperties.entrySet()) {
                if (entry.getValue() instanceof String) {
                    filteredEventProperties.put(entry.getKey(), entry.getValue().toString());
                }
            }
        }
        return filteredEventProperties;
    }
}
