package com.example.easyworks.activities

import android.app.*
import android.content.Intent
import android.os.Bundle
import android.content.Context
import android.os.Build
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.easyworks.models.NotificationModel
import com.example.easyworkscrud.R

import com.example.easyworkscrud.databinding.ActivityServiceNotificationBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Service_Notification : AppCompatActivity()
{



    private lateinit var binding : ActivityServiceNotificationBinding
    private lateinit var database: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?)
    {



        super.onCreate(savedInstanceState)
        binding = ActivityServiceNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        createNotificationChannel()
        binding.notifysendbtn.setOnClickListener { scheduleNotification() }



    }



    private fun scheduleNotification()
    {
        val intent = Intent(applicationContext, Notification1::class.java)
        val title = binding.notifyTitle.text.toString()
        val category = binding.notifyCat.text.toString()
        val description = binding.notifydesc.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, category)
        intent.putExtra(descriptionExtra,description)

        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 5)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        // Store notification to Firebase Database
        val id = database.child("NotificationData").push().key
        val notification = NotificationModel(id, title, category, description)
        if (id != null) {
            database.child("Notifications").child(id).setValue(notification)
        }

        showAlert(title, category,description)
    }

    private fun showAlert(title: String, category: String, description: String)
    {

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + category +"\nDescription: " + description )
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }





    private fun createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notif Channel"
            val desc = "A Description of the Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance)
            channel.description = desc
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        var backbtn = findViewById<ImageButton>(R.id.backbtn)
        backbtn.setOnClickListener({
            val intent = Intent(this, addService::class.java)
            startActivity(intent)
        })

    }
}

