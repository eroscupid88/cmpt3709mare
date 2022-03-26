package com.example.cmpt370_9mare.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.cmpt370_9mare.R


private val NOTIFICATION_ID = 0

/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.calendar_app_notification_id)
    ).setSmallIcon(R.drawable.ic_calendar_black_24dp)
        .setContentTitle(
            applicationContext
                .getString(R.string.notification_title)
        )
        .setContentText(messageBody)
        .setPriority(NotificationCompat.PRIORITY_HIGH)


    // build the notification
    notify(NOTIFICATION_ID, builder.build())


}


/**
 * Cancel all notification
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}