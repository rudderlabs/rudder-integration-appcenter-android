package com.rudderstack.android.sample.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rudderlabs.android.sample.kotlin.R
import com.rudderstack.android.sdk.core.RudderProperty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Tc: 1. Sending simple track call with event name

        MainApplication.rudderClient.track("Shop closed")

        //Tc: 2. Sending simple track call with event name and properties

        val property0 = RudderProperty()
        property0.put("String Key", "String value")
        property0.put("Number Key", 10000)

        MainApplication.rudderClient.track("Shop closed", property0)

        //Tc: 3. Sending track call with event name and properties by setting the priority as "Critical"

        val property00 = RudderProperty()
        property00.put("Category", "Carryminati 2.0")
        property00.put("Platform", "Youtube")

        val property01 = RudderProperty()
        property01.put("Category", "Kya karu main marjaun")

        MainApplication.rudderClient.track("Video Clicked", property00)

        MainApplication.rudderClient.track("Video Clicked once more", property01)

      //Tc: 4. Sending track call with event name and properties by setting the priority as "Normal"
        val property20 = RudderProperty()
        property20.put("Category", "Hip Hop")
        property20.put("Platform", "Youtube")

        val property30 = RudderProperty()
        property30.put("Category", "Rasode me kon tha")

        MainApplication.rudderClient.track("Song Purcahsed", property20)

        MainApplication.rudderClient.track("Song Purcahsed once more", property30)

        //Tc: 5. Sending 5 track call, some with "Normal" priority and some with "Critical" priority and by giving some value to "Transmission Interval"

        MainApplication.rudderClient.track("Normal Event 1")
        MainApplication.rudderClient.track("Critical Event 1")
        MainApplication.rudderClient.track("Normal Event 2")
        MainApplication.rudderClient.track("Normal Event 3")
        MainApplication.rudderClient.track("Critical Event 2")


        //Tc: 6. Sending track call with event name and in properties some key/value length longer than 125 characters.

        val property3 = RudderProperty()
        property3.put("mbkdbakckdbkcbandcandcndncdbakcbdbcbdkjbcldncln kdnkcnnlkcdnlkcndnckndncldnlcnldnckdnclnncndlclndcndncdnjkcdkncknsdkhcshdcnndvkkvnsnv", "Value")
        property3.put("details", "this is longer key")

        val property1 = RudderProperty()
        property1.put("Category", "mbkdbakckdbkcbandcandcndncdbakcbdbcbdkjbcldncln kdnkcnnlkcdnlkcndnckndncldnlcnldnckdnclnncndlclndcndncdnjkcdkncknsdkhcshdcnndvkkvnsnv")


        MainApplication.rudderClient.track("Too Long Key", property3)
        MainApplication.rudderClient.track("Too Long Value", property1)


        //Tc: 7. Sending track call with event name length longer than 256 characters.

        val property2 = RudderProperty()
        property2.put("mbkdbakckdbkcbandcandcndncdbakcbdbcbdkjbcldncln kdnkcnnlkcdnlkcndnckndncldnlcnldnckdnclnncndlclndcndncdnjkcdkncknsdkhcshdcnndvkkvnsnv", "Value")
        property2.put("details", "this is longer event name")

        MainApplication.rudderClient.track("kbkdbakckdbkcbandcandcndncdbakcbdbcbdkjbcldncln kdnkcnnlkcdnlkcndnckndncldnlcnldnckdnclnncndlclndcndncdnjkcdkncknsdkhcshdcnndvkkvnsnvhkjbdckadkjhvcjkdbcbdb kjsdhvdvs,mbvkjsfkvbsbvdbvzjksklfbvmnfdlavjvsbdvmmfsnvkhfdvfgweiohgjkvbjkhrivnkvbdsjvvbnvlhfvnfbvjkhvfbv", property2)


        //Tc: 8. Sending track call with event name and in properties more than 20 key:value pair

        val property = RudderProperty()
        property.put("cam1", "val1")
        property.put("cam2", "val1")
        property.put("cam3", "val1")
        property.put("cam4", "val1")
        property.put("cam5", "val1")
        property.put("cam6", "val1")
        property.put("cam7", "val1")
        property.put("cam8", "val1")
        property.put("cam9", "val1")
        property.put("cam10", "val1")
        property.put("cam11", "val1")
        property.put("cam12", "val1")
        property.put("cam13", "val1")
        property.put("cam14", "val1")
        property.put("cam16", "val1")
        property.put("cam17", "val1")
        property.put("cam18", "val1")
        property.put("cam19", "val1")
        property.put("cam20", "val1")
        property.put("cam21", "val1")
        property.put("cam22", "val1")

        MainApplication.rudderClient.track("Event with 22 properties", property)




        //Tc: 9. Sending track call with event name and in properties where value of a key is Integer/String/Double/Empty/Array/Object

        val num = arrayOf(1, 2, 3, 4)
        val props = RudderProperty()
        props.put("object key", "object value")

        val property4 = RudderProperty()
        property4.put("Key", 1000)
        property4.put("details", "this is event")
        val property5 = RudderProperty()
        property5.put("Key", "main fetures")
        property5.put("details", "this is event")
        val property6 = RudderProperty()
        property6.put("Key", 3390.88)
        property6.put("details", "this is event")
        val property7 = RudderProperty()
        property7.put("Key", null)
        property7.put("details", "this is event")
        val property8 = RudderProperty()
        property8.put("Key", "")
        property8.put("details", "this is event")
        val property9 = RudderProperty()
        property9.put("Key",num)
        property9.put("details", "this is event")
        val property10 = RudderProperty()
        property10.put("Key",props)
        property10.put("details", "this is event")

        MainApplication.rudderClient.track("Integer Value again", property4)
        MainApplication.rudderClient.track("String Value again", property5)
        MainApplication.rudderClient.track("Double Value again", property6)
        MainApplication.rudderClient.track("Null Value again", property7)
        MainApplication.rudderClient.track("Empty Value again", property8)
        MainApplication.rudderClient.track("Array Value again", property9)
        MainApplication.rudderClient.track("Object Value again", property10)



        //Tc: 10. Sending duplicate track events with some matching keys or mismatching keys

        val property11 = RudderProperty()
        property11.put("String Key", "Other String Value")
        property11.put("Number Key", 2000)

        val property12 = RudderProperty()
        property12.put("String Key", "Other String Value")
        property12.put("Number Key", 1400)

        MainApplication.rudderClient.track("Shop closed", property11)
        MainApplication.rudderClient.track("Shop closed", property12)



        //Tc: 11. Sending screen call with screen name

        MainApplication.rudderClient.screen(
        "MainActivity",
        "HomeScreen",
        RudderProperty().putValue("foo", "bar"),
        null
        )


        //Tc: 12. Sending screen call with screen name and properties by setting the priority as "normal"

        MainApplication.rudderClient.screen(
        "Home Page",
        "Home Category",
        RudderProperty().putValue("details", "this is a home page call"),
        null
        )
    }
}
