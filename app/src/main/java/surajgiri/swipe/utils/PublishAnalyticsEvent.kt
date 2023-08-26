package surajgiri.swipe.utils

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import surajgiri.core.model.AnalyticsEvent

class PublishAnalyticsEvent (private val analyticsEvent: AnalyticsEvent) {

    private val firebaseAnalytics = Firebase.analytics

    private fun firebaseEvent(analyticsEvent: AnalyticsEvent) {
        firebaseAnalytics.logEvent(analyticsEvent.name, analyticsEvent.params)
    }


    fun publish(){
        firebaseEvent(analyticsEvent)
    }

}
