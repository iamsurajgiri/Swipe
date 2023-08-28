package surajgiri.swipe.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.Constants.MessageNotificationKeys
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import surajgiri.core.model.AnalyticsEvent
import surajgiri.core.model.NotificationItem
import surajgiri.swipe.MainActivity
import surajgiri.swipe.R
import surajgiri.swipe.utils.PublishAnalyticsEvent
import surajgiri.swipe.utils.isNotNullOrNotEmpty


class SwipeService : FirebaseMessagingService() {


    override fun handleIntent(intent: Intent) {
        val remoteMessage = RemoteMessage(intent.extras)
        if (intent.extras!=null) {
            intent.putExtra(MessageNotificationKeys.ENABLE_NOTIFICATION, "0")
            intent.putExtra(keyWithOldPrefix(MessageNotificationKeys.ENABLE_NOTIFICATION), "0")
            super.handleIntent(intent)
        }

        remoteMessage.data.let { notificationData->
            val notificationItem = NotificationItem(
                title = "${notificationData["title"]}",
                image = notificationData["image"],
                desc = notificationData["desc"],
            )
            if (remoteMessage.notification==null){
                createNotifications(notificationItem)
            }
        }
        remoteMessage.notification.let {notificationData->
            val title:String = notificationData?.title ?: "none"
            //val imageUri: Uri? = notificationData?.imageUrl

            val notificationItem = NotificationItem(
                title = title,
                null,
                desc = notificationData?.body
            )
            if (title!="none"){
                createNotifications(notificationItem)
            }
        }
    }





    private fun createNotifications(notificationItem: NotificationItem) {
        val topic = "testing"
        val newIntent = Intent(this, MainActivity::class.java)
        newIntent.putExtra("title",notificationItem.title)
        newIntent.action = System.currentTimeMillis().toString()
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, newIntent,
            PendingIntent.FLAG_IMMUTABLE)

        Log.i("MainActivity", "FCM createNotifications: ${newIntent.extras}")
        val channelId = "$topic channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(notificationItem.title)
            .setContentText(notificationItem.desc)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(notificationItem.desc)
                .setBigContentTitle(notificationItem.title)
                .setSummaryText(topic)
            )
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                topic,
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        if (notificationItem.image.isNotNullOrNotEmpty() && notificationItem.image?.lowercase()?.contains("icon") == false){
            Glide.with(this)
                .asBitmap()
                .load(notificationItem.image)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val bigStyleImage = NotificationCompat.BigPictureStyle().bigPicture(resource)
                        notificationBuilder.setStyle(bigStyleImage)
                        notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
                        notificationManager.notify(
                            101 /* ID of notification */,
                            notificationBuilder.build()
                        )

                        PublishAnalyticsEvent(
                            AnalyticsEvent(
                                "notification_created",
                                bundleOf(
                                    FirebaseAnalytics.Param.CONTENT to notificationItem.title
                                )
                            )
                        ).publish()
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }else{
            notificationManager.notify(
                101, /* ID of notification */
                notificationBuilder.build()
            )
            PublishAnalyticsEvent(
                AnalyticsEvent(
                    "notification_created",
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT to notificationItem.title
                    )
                )
            ).publish()
        }

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //todo save the token
    }


    private fun keyWithOldPrefix(key: String): String {
        if (!key.startsWith(MessageNotificationKeys.NOTIFICATION_PREFIX)) {
            return key
        }

        return key.replace(
            MessageNotificationKeys.NOTIFICATION_PREFIX,
            MessageNotificationKeys.NOTIFICATION_PREFIX_OLD
        )
    }
}