package surajgiri.swipe.utils

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object FirebaseDefaultAnalytics {
    /*get some default event parameters to send with each analytics event
    (Analytics > Events > Manage Custom Definitions > Create Custom Dimensions) refer this to create
    default params*/
    private fun getDefaultParams(): Bundle {
        return bundleOf(
            "device_battery" to "1%"
        )
    }

    //Pavan FCM
    //eWJQejwkSJmCUco3A6CKfl:APA91bE4PtR1wYJxYWo8F2vgGknRKxrq3qsGVeUizuyZmvfefBF4RxBAipvwek8IIAbsn9ccRFAeEBfDyHzpcPSOJHqunApyoVeYRcXPci2sHm3Mg0HkJBOgXw2aTC2ZuBCH1jT-JX0J
    //Adarsh FCM
    //dYK5tkxFTZmN2sDI3WXcQ_:APA91bEpy3H4C01c_eoHYueMcusBW_tulG3-uQGc9vpdAI0eMkTBe0K3fJNl9N5255we8OkxomOV7iDh67P-2sGmFFB4BkCqQTjFD3vgLumoxzDyL_ZH8PRk4uuwxC7RUeJQTlcj9nU3
    //Ballu FCM
    //fu4sgCYfQcGZGToq-SlpiY:APA91bEA828uU1tMV1dCdhLV6bBiMr1SHLmT3ZFjBmvZx0WP1FsCj1JJVhKo-VzbH6JM3nAZf9cWG2p2qlnZQwigoSj1N-VpVqbnmIBk5wcTi5wv6m7uqSKkSLzaS5epYmitqGXFy4gj


    // get some user id to identify analytics event to specific user
    private fun getUserId(): String {
        return "sample_user_ballu"
    }

    /* get some user group identification properties, to categories the event based on these groups
    https://console.firebase.google.com/project/_/analytics/userproperty
    ( Analytics > Events > Manage Custom Definitions > Create Custom Dimensions) refer this to create the
    custom user properties first in the firebase console, one can create 25 unique user properties */
    private fun getUserProperties():HashMap<String,String>{
        return hashMapOf(
            "user_type" to "premium"
        )
    }

    fun setFirebaseDefaults(
        userId: String?=null,
        defaultParameters: Bundle? =null,
        userProperties: HashMap<String,String>?=null
    ){
        val analytics = Firebase.analytics
        val properties = userProperties?:getUserProperties()
        val user = userId?:getUserId()
        val defaultParams = defaultParameters?:getDefaultParams()
        analytics.apply {
            setUserId(user)
            setDefaultEventParameters(defaultParams)
            properties.forEach { this.setUserProperty(it.key,it.value) }
        }
    }
}