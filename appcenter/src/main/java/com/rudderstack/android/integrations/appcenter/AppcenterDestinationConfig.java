package com.rudderstack.android.integrations.appcenter;

import java.util.Map;

public class AppcenterDestinationConfig {

    final String appSecret;
    final String transmissionLevel;
    final Map<String, Integer> eventMap;

    public AppcenterDestinationConfig(String appSecret, String transmissionLevel, Map<String, Integer> eventMap) {
        this.appSecret = appSecret;
        this.transmissionLevel = transmissionLevel;
        this.eventMap = eventMap;
    }
}
