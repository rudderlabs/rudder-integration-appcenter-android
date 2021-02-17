# What is RudderStack?

[RudderStack](https://rudderstack.com/) is a **customer data pipeline** tool for collecting, routing and processing data from your websites, apps, cloud tools, and data warehouse.

More information on RudderStack can be found [here](https://github.com/rudderlabs/rudder-server).

## Integrating Visual Studio App Center for RudderStack's Android SDK

1. Add [Appcenter](https://appcenter.ms) as a destination in the [RudderStack dashboard](https://app.rudderstack.com/) and define the app secret key.

2. Add the following `dependencies` to your `app/build.gradle` file as shown:

```groovy
implementation 'com.rudderstack.android.sdk:core:1.+'
implementation 'com.rudderstack.android.integration:appcenter:1.0.1'
implementation 'com.google.code.gson:gson:2.8.6'

// Appcenter dependency
implementation "com.microsoft.appcenter:appcenter-analytics:4.1.0"
```

3. Also add the below `repositories` tag in your `app/build.gradle` as shown:

```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/rudderstack/rudderstack"
    }
}
```

4. Make sure that the `minSdkVersion` in your `app/build.gradle` is at least `21`:

```groovy
defaultConfig {
        minSdkVersion 21
}
```

5. Finally change the initialization of your `RudderClient` in your `Application` class:

```groovy
val rudderClient = RudderClient.getInstance(
    this,
    <YOUR_WRITE_KEY>,
    RudderConfig.Builder()
        .withDataPlaneUrl(<YOUR_DATA_PLANE_URL>)
        .withFactory(AppcenterIntegrationFactory.FACTORY)
        .build()
)
```

## Send Events

Follow the steps from the [RudderStack Android SDK](https://github.com/rudderlabs/rudder-sdk-android).

## Contact Us

If you come across any issues while configuring or using this integration, please feel free to start a conversation on our [Slack](https://resources.rudderstack.com/join-rudderstack-slack) channel. We will be happy to help you.
