package surajgiri.core.model

import android.os.Bundle

data class AnalyticsEvent(
    val name: String,
    val params: Bundle? = Bundle()
)