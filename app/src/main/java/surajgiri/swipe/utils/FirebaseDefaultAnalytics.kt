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


    // get some user id to identify analytics event to specific user
    private fun getUserId(): String {
        return "sample_user_1"
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