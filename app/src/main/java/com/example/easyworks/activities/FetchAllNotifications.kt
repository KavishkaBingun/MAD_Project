package com.example.easyworks.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyworks.adapters.NotificationAdapter
import com.example.easyworks.models.NotificationModel
import com.example.easyworkscrud.R
import com.google.firebase.database.*

class FetchAllNotifications : AppCompatActivity() {

    private lateinit var notifyRecyclerView: RecyclerView
    private lateinit var textviewloadingData: TextView
    private lateinit var notifylist : ArrayList<NotificationModel>
    private lateinit var dbref : DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_all_notifications)

        var servicebtn = findViewById<ImageButton>(R.id.servicenotselbtn1)
        servicebtn.setOnClickListener({
            val intent = Intent(this, addService::class.java)
            startActivity(intent)
        })

        var ratebtn = findViewById<ImageButton>(R.id.ratingnotselbtn)
        ratebtn.setOnClickListener({
            val intent = Intent(this, FetchingAct::class.java)
            startActivity(intent)
        })

        var profbtn = findViewById<ImageButton>(R.id.profilenotselbtn)
        profbtn.setOnClickListener({
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        })

        var homebtn = findViewById<ImageButton>(R.id.homeselbtn)
        homebtn.setOnClickListener({
            val intent = Intent(this, ViewServices::class.java)
            startActivity(intent)
        })




        notifyRecyclerView = findViewById(R.id.notifyrecycle)
        notifyRecyclerView.layoutManager = LinearLayoutManager(this)
        notifyRecyclerView.setHasFixedSize(true)
        textviewloadingData = findViewById(R.id.loadingdata)

        notifylist = arrayListOf<NotificationModel>()

        getServiceData()


    }

    private fun getServiceData(){
        notifyRecyclerView.visibility = View.GONE
        textviewloadingData.visibility = View.VISIBLE

        dbref = FirebaseDatabase.getInstance().getReference("Notifications")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notifylist.clear()
                if(snapshot.exists()){
                    for(serviceSnap in snapshot.children){
                        val notificationdata = serviceSnap.getValue(NotificationModel::class.java)
                        notifylist.add(notificationdata!!)
                    }
                    val sadapter = NotificationAdapter(notifylist)
                    notifyRecyclerView.adapter = sadapter

                    sadapter.setOnItemClickListener(object : NotificationAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchAllNotifications,FetchNotifications::class.java)

                            //put extras
                            intent.putExtra("id",notifylist[position].id)
                            intent.putExtra("title",notifylist[position].title)
                            intent.putExtra("category",notifylist[position].category)
                            intent.putExtra("description",notifylist[position].description)

                            startActivity(intent)
                        }

                    })

                    notifyRecyclerView.visibility = View.VISIBLE
                    textviewloadingData.visibility = View.GONE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }



}
