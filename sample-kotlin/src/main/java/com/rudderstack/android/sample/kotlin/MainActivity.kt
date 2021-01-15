package com.rudderstack.android.sample.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rudderlabs.android.sample.kotlin.R
import com.rudderstack.android.sdk.core.RudderProperty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val screenProperties = RudderProperty();
        screenProperties.put("path", "/testing")
        screenProperties.put("device", "android")
        MainApplication.rudderClient.screen("Shop testing")
        val property = RudderProperty()
        property.put("key_1", "val_1")
        property.put("key_2", "val_2")
        MainApplication.rudderClient.track("Shop closed", property)
        val childProperty = RudderProperty()
        childProperty.put("key_c_1", "val_c_1")
        childProperty.put("key_c_2", "val_c_2")
        property.put("child_key", childProperty)
        MainApplication.rudderClient.track("Shopping Done", property)
    }
}
