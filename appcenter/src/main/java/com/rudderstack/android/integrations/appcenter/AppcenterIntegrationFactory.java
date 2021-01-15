package com.rudderstack.android.integrations.appcenter;

import androidx.annotation.Nullable;

import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;
import com.rudderstack.android.sdk.core.RudderIntegration;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.RudderMessage;


public class AppcenterIntegrationFactory extends RudderIntegration<RudderClient> {

    private static final String APPCENTER_KEY = "Appcenter";

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

    }

    @Override
    public void reset() {

    }

    @Override
    public void dump(@Nullable RudderMessage element) {
        try {
            if (element != null) {

            }
        } catch (Exception e) {
            RudderLogger.logError(e);
        }
    }
}