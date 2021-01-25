# What is Rudder?

**Short answer:** 
Rudder is an open-source Segment alternative written in Go, built for the enterprise.

**Long answer:** 
Rudder is a platform for collecting, storing and routing customer event data to dozens of tools. Rudder is open-source, can run in your cloud environment (AWS, GCP, Azure or even your data-centre) and provides a powerful transformation framework to process your event data on the fly.

Released under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Getting Started with Appcenter Integration of Android SDK
1. Add [Appcenter](https://appcenter.ms) as a destination in the [Dashboard](https://app.rudderstack.com/) and define app secret key.

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

4. Make sure that the `minSdkVersion` in your `app/build.gradle` is at least `21`.
```groovy
defaultConfig {
        minSdkVersion 21
}
```

5. Finally change the initialization of your `RudderClient` in your `Application` class.
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
Follow the steps from [Rudder Android SDK](https://github.com/rudderlabs/rudder-sdk-android)

## Contact Us
If you come across any issues while configuring or using RudderStack, please feel free to [contact us](https://rudderstack.com/contact/) or start a conversation on our [Discord](https://discordapp.com/invite/xNEdEGw) channel. We will be happy to help you.
