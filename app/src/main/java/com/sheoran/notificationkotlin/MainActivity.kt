package com.sheoran.notificationkotlin

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    lateinit var tl: EditText
    lateinit var ms:EditText
    lateinit var button2: Button

    var CHANNEL_ID = "trchnaical"
    var name: CharSequence = "my_channel"

    var Description = " THis is my  channel"
    var NOTIFICATION_ID = 201

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tl = findViewById(R.id.editTextText)
        ms = findViewById(R.id.editTextText2)
        button2 = findViewById(R.id.button)

       button2.setOnClickListener {
           SendOnChannel()
       }
    }

    private fun SendOnChannel() {
        val tl1 = tl!!.text.toString()
        val ms1 = ms!!.text.toString()
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val notificationManager = applicationContext
                .getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importace = NotificationManager.IMPORTANCE_HIGH
                val mchannel = NotificationChannel(CHANNEL_ID, name, importace)
                mchannel.description = Description
                mchannel.enableLights(true)
                mchannel.enableVibration(true)
                mchannel.vibrationPattern =
                    longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 100)
                mchannel.setShowBadge(true)
                notificationManager.createNotificationChannel(mchannel)
            }
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.do_not_touch)
                .setContentTitle(tl1)
                .setContentText(ms1)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.images))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
            val intent = Intent(applicationContext, MainActivity2::class.java)
            intent.putExtra("tittle", tl1)
            intent.putExtra("message", ms1)
            val intent1 = PendingIntent.getActivity(
                this, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            builder.setContentIntent(intent1)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        } else {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 234)
        }
    }
}