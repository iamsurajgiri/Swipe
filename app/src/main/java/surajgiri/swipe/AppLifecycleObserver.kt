package surajgiri.swipe

import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import surajgiri.core.model.AnalyticsEvent
import surajgiri.swipe.utils.PublishAnalyticsEvent
import java.util.Calendar
import java.util.concurrent.TimeUnit

class AppLifecycleObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        val usageStart = NotificationPreferenceHelper.getLong(NotificationPreferenceHelper.APP_IN_FOREGROUND)
        val backgroundTimestamp = NotificationPreferenceHelper.getLong(NotificationPreferenceHelper.APP_IN_BACKGROUND)
        if (usageStart>0 && backgroundTimestamp>0){
            val currentTimestamp = Calendar.getInstance().time.time
            val differenceInMillis = currentTimestamp - backgroundTimestamp
            val differenceInSeconds = TimeUnit.MILLISECONDS.toSeconds(differenceInMillis)
            if (differenceInSeconds >= 900) {
                val totalUsage = backgroundTimestamp-usageStart
                PublishAnalyticsEvent(
                    AnalyticsEvent(
                        "notification_launch_usage",
                        bundleOf(
                            "duration" to totalUsage
                        )
                    )
                ).publish()
                NotificationPreferenceHelper.removeKey(NotificationPreferenceHelper.APP_IN_FOREGROUND)
                NotificationPreferenceHelper.removeKey(NotificationPreferenceHelper.APP_IN_BACKGROUND)

                Log.i("Lifecycle", "onAppForegrounded: Notification launch usage sent")
            }
            Log.i("Lifecycle", "onAppForegrounded  Usage Start: $usageStart Background Time: $differenceInSeconds")
        }
        Log.i("Lifecycle", "onAppForegrounded  Usage Start: $usageStart")


    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        val usageStart = NotificationPreferenceHelper.getLong(NotificationPreferenceHelper.APP_IN_FOREGROUND)
        if (usageStart>0){
            val currentTimestamp = Calendar.getInstance().time.time
            NotificationPreferenceHelper.saveLong(NotificationPreferenceHelper.APP_IN_BACKGROUND,currentTimestamp)
            Log.i("Lifecycle", "onAppBackgrounded")
        }
    }

}