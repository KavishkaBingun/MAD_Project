package com.example.easyworks.activities

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.easyworkscrud.R


const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"
const val descriptionExtra = "descriptionExtra"

class Notification1 : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setStyle(NotificationCompat.BigTextStyle().bigText(intent.getStringExtra(descriptionExtra)))
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)

    }

}
