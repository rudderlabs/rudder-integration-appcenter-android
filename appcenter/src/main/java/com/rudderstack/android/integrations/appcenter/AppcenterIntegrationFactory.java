package com.rudderstack.android.integrations.appcenter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.rudderstack.android.sdk.core.MessageType;
import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;
import com.rudderstack.android.sdk.core.RudderIntegration;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.RudderMessage;

import com.google.gson.JsonArray;

import java.lang.reflect.Type;
import java.util.Map;

public class AppcenterIntegrationFactory extends RudderIntegration<Analytics> {
    private static final String APPCENTER_KEY = "App Center";
    private AppcenterDestinationConfig destinationConfig;

    public static Factory FACTORY = new Factory() {
        @Override
        public RudderIntegration<?> create(Object settings, RudderClient client, RudderConfig rudderConfig) {
            return new AppcenterIntegrationFactory(settings);
        }

        @Override
        public String key() {
            return APPCENTER_KEY;
        }
    };

    private AppcenterIntegrationFactory(@NonNull Object config) {
        if (RudderClient.getApplication() == null) {
            RudderLogger.logError("Application is null. Aborting Appcenter initialization.");
            return;
        }

        // deserialize the destination config json into AppCenterDestinationConfig object
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<AppcenterDestinationConfig> deserializer =
                new JsonDeserializer<AppcenterDestinationConfig>() {
                    @Override
                    public AppcenterDestinationConfig deserialize(
                            JsonElement json,
                            Type typeOfT,
                            JsonDeserializationContext context
                    ) throws JsonParseException {
                        JsonObject jsonObject = json.getAsJsonObject();
                        JsonArray eventPriorityMap = (JsonArray) (jsonObject.get("eventPriorityMap"));
                        Map<String, Integer> eventMap = Utils.getEventMap(eventPriorityMap);

                        return new AppcenterDestinationConfig(
                                jsonObject.get("appSecret").getAsString(),
                                jsonObject.get("transmissionLevel").getAsString(),
                                eventMap
                        );
                    }
                };
        gsonBuilder.registerTypeAdapter(AppcenterDestinationConfig.class, deserializer);
        Gson customGson = gsonBuilder.create();
        this.destinationConfig = customGson.fromJson(customGson.toJson(config), AppcenterDestinationConfig.class);

        // initializing appcenter sdk
        if (!TextUtils.isEmpty(this.destinationConfig.transmissionLevel)) {
            Analytics.setTransmissionInterval(
                    Integer.parseInt(this.destinationConfig.transmissionLevel) * 60
            );
        }

        // Everything looks good. Initiate the SDK
        AppCenter.start(
                RudderClient.getApplication(),
                this.destinationConfig.appSecret,
                Analytics.class
        );
    }

    private void processRudderEvent(RudderMessage element) {
        String type = element.getType();
        if (type != null) {
            switch (type) {
                case MessageType.TRACK:
                    String eventName = element.getEventName();
                    if (eventName != null) {
                        Map<String, String> eventProperties = Utils.filterEventProperties(
                                element.getProperties()
                        );
                        Integer critical = this.destinationConfig.eventMap.get(eventName);
                        if (critical != null) {
                            // criticality for this event is mapped in the dashboard
                            // track with mentioned criticality
                            Analytics.trackEvent(eventName, eventProperties, critical);
                            break;
                        }
                        // track event with DEFAULT criticality
                        Analytics.trackEvent(eventName, eventProperties);
                    }
                    break;
                case MessageType.SCREEN:
                    Map<String, String> screenProperties =
                            Utils.filterEventProperties(element.getProperties());
                    if (screenProperties != null) {
                        Analytics.trackEvent(
                                String.format("Viewed %s screen", screenProperties.get("name")),
                                screenProperties
                        );
                    }
                    break;
                default:
                    RudderLogger.logWarn("MessageType is not specified or supported");
                    break;
            }
        }
    }

    @Override
    public void reset() {
        RudderLogger.logDebug("Appcenter does not support the reset");
    }

    @Override
    public void dump(@Nullable RudderMessage element) {
        try {
            if (element != null) {
                processRudderEvent(element);
            }
        } catch (Exception e) {
            RudderLogger.logError(e);
        }
    }

    @Override
    public Analytics getUnderlyingInstance() {
        return Analytics.getInstance();
    }
}