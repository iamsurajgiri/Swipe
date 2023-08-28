package surajgiri.swipe

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import surajgiri.core.model.AnalyticsEvent
import surajgiri.swipe.databinding.ActivityMainBinding
import surajgiri.swipe.utils.PublishAnalyticsEvent
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Firebase.analytics.setUserProperty("notification_status","enabled")
            // FCM SDK (and our app) can post notifications.
        } else {
            Firebase.analytics.setUserProperty("notification_status","disabled")
            Toast.makeText(this,"You will not receive any notifications",Toast.LENGTH_SHORT)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.listProductFragment)

        registerFCMToken()
        askNotificationPermission()

        val notificationTitle = intent.extras?.getString("title")
        if (notificationTitle!=null){
            PublishAnalyticsEvent(
                AnalyticsEvent(
                    "notification_opened",
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT to notificationTitle
                    )
                )
            ).publish()
            val currentTimestamp = Calendar.getInstance().time.time
            NotificationPreferenceHelper.saveLong(NotificationPreferenceHelper.APP_IN_FOREGROUND,currentTimestamp)
            Toast.makeText(this, "Notification Opened", Toast.LENGTH_SHORT).show()
        }


    }

    private fun registerFCMToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete && it.isSuccessful){
                Log.i("SwipeFCM", "registerFCMToken: ${it.result}")
            }
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                Firebase.analytics.setUserProperty("notification_status","enabled")
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Firebase.analytics.setUserProperty("notification_status","disabled")
                Toast.makeText(this, "Please allow notification permission!", Toast.LENGTH_SHORT).show()
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }else{
            Firebase.analytics.setUserProperty("notification_status","enabled")

        }
    }
}
