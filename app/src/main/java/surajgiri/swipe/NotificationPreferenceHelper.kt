package surajgiri.swipe

import android.content.Context
import android.content.SharedPreferences
import java.lang.Exception

object NotificationPreferenceHelper {

    private lateinit var preferences: SharedPreferences

    private const val NOTIFICATION_SHARED_PREFERENCES = "notification_shared_preferences"
    const val APP_IN_BACKGROUND = "app_in_background"
    const val APP_IN_FOREGROUND = "app_in_foreground"
    fun init(context: Context) {
        preferences = context.getSharedPreferences(NOTIFICATION_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    fun getLong(key: String): Long {
        return preferences.getLong(key, 0)
    }

    fun saveLong(key: String,number: Long) {
        preferences.edit().putLong(key, number).apply()
    }

    fun removeKey(key : String) {
        try {
            preferences.edit().remove(key).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}