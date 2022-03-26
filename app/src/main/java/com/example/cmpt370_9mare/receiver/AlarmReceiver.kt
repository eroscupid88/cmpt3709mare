package com.example.cmpt370_9mare.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.util.sendNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            Toast.makeText(context, context.getText(R.string.event_coming_up), Toast.LENGTH_SHORT)
                .show()
        }

        val notificationManager = context?.let {
            ContextCompat.getSystemService(
                it, NotificationManager::class.java
            )
        } as NotificationManager
        notificationManager.sendNotification(
            context.getText(R.string.event_coming_up).toString(), context
        )
    }
}