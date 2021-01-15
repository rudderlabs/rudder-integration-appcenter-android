package com.rudderstack.android.integrations.appcenter;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.Flags;
import com.microsoft.appcenter.analytics.Analytics;
import com.rudderstack.android.sdk.core.MessageType;
import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;
import com.rudderstack.android.sdk.core.RudderIntegration;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.RudderMessage;


import java.util.Map;
import java.util.Set;


public class AppcenterIntegrationFactory extends RudderIntegration<Analytics> {

    private static final String APPCENTER_KEY = "AppCenter";

    private Analytics analytics;
    private AppcenterDestinationConfig destinationConfig;

    private Set<String> criticalEvents;
    private Set<String> normalEvents;

    public static Factory FACTORY = new Factory() {
        @Override
        public RudderIntegration<?> create(Object settings, RudderClient client, RudderConfig rudderConfig) {
            return new AppcenterIntegrationFactory(settings, rudderConfig);
        }

        @Override
        public String key() {
            return APPCENTER_KEY;
        }
    };

    private AppcenterIntegrationFactory(Object config, RudderConfig rudderConfig) {

        if (RudderClient.getApplication() == null) {
            RudderLogger.logError("Application is null. Aborting Appcenter initialization.");
            return;
        }

        if (config == null) {
            RudderLogger.logError("Invalid config. Aborting Appcenter initialization.");
            return;
        }

        // parse destination config
        Gson gson = new Gson();
        this.destinationConfig = gson.fromJson(gson.toJson(config), AppcenterDestinationConfig.class);

        if (TextUtils.isEmpty(this.destinationConfig.appSecret)) {
            RudderLogger.logError("Invalid app secret. Aborting Appcenter initialization.");
            return;
        }

        // process event priorities
        if (this.destinationConfig.eventPriorityMap != null) {
            criticalEvents = Utils.getEventSet(this.destinationConfig.eventPriorityMap, "Critical");
            normalEvents = Utils.getEventSet(this.destinationConfig.eventPriorityMap, "Normal");
        }

        if (!TextUtils.isEmpty(this.destinationConfig.transmissionLevel)) {
            analytics.setTransmissionInterval(Integer.valueOf(this.destinationConfig.transmissionLevel) * 60);
        }
        AppCenter.start(RudderClient.getApplication(), this.destinationConfig.appSecret, Analytics.class);

    }

    private void processRudderEvent(RudderMessage element) throws Exception {
        String type = element.getType();
        if (type != null) {
            switch (type) {
                case MessageType.TRACK:
                    String eventName = element.getEventName();
                    if (eventName != null) {
                        Map<String, String> eventProperties = Utils.filterEventProperties(element.getProperties());
                        if (eventProperties == null) {
                            analytics.trackEvent(eventName);
                            break;
                        }
                        if (criticalEvents.contains(eventName)) {
                            analytics.trackEvent(eventName, eventProperties, Flags.CRITICAL);
                            break;
                        }
                        if (normalEvents.contains(eventName)) {
                            analytics.trackEvent(eventName, eventProperties, Flags.NORMAL);
                            break;
                        }
                        analytics.trackEvent(eventName, eventProperties);
                    }
                    break;
                case MessageType.SCREEN:
                    Map<String, String> screenProperties = Utils.filterEventProperties(element.getProperties());
                    if (screenProperties != null) {
                        analytics.trackEvent("Viewed a screen", screenProperties);
                        break;
                    }
                    analytics.trackEvent("Viewed a screen");
                    break;
                default:
                    RudderLogger.logWarn("AppcenterIntegrationFactory: MessageType is not specified");
                    break;
            }
        }
    }

    @Override
    public void flush() {
        RudderLogger.logDebug("Inside flush");
    }

    @Override
    public void reset() {
        RudderLogger.logDebug("Inside reset");
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
        return this.analytics;
    }

}