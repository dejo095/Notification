package com.example.dejan.notification

import android.app.Notification
import android.app.NotificationChannel
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import kotlinx.android.synthetic.main.activity_main.*
import android.app.AppComponentFactory
import android.widget.RemoteViews


class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "Dejos testing"
    private val description = "If I can have a moment..."


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        btn_notify.setOnClickListener {

            // what will notification start
            val intent = Intent(this, MainActivity::class.java)
            // intent cannot be passed directly to notification
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            // Our custom resource layout made for notification
            val contentView = RemoteViews(packageName,R.layout.notification_layout)
            contentView.setTextViewText(R.id.tv_title,"Dejos Title")
            contentView.setTextViewText(R.id.tv_content,"Text notification")

            // create channel
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)

            // control the led light and vibration
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)

            // create notification
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                        .setContent(contentView)
//                        .setContentTitle("Some Title here")
//                        .setContentText("Some text here as well")
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher))
                        .setContentIntent(pendingIntent)

            // this invokes the notification
            notificationManager.notify(1234,builder.build())

        }



    }


}
