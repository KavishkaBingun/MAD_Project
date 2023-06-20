package com.example.easyworks.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.easyworkscrud.R
import com.google.firebase.database.FirebaseDatabase



private lateinit var tvServTitle: TextView
// private lateinit var tvServCat: TextView
private lateinit var tvServPrice: TextView
private lateinit var tvFreeName : TextView

class FetchNotifications : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_notifications)

        var servicebtn = findViewById<ImageButton>(R.id.servicenotselbtn)
        servicebtn.setOnClickListener({
            val intent = Intent(this, addService::class.java)
            startActivity(intent)
        })

        var homebtn = findViewById<ImageButton>(R.id.homenotselbtn)
        homebtn.setOnClickListener({
            val intent = Intent(this, ViewServices::class.java)
            startActivity(intent)
        })

        var ratepage = findViewById<ImageButton>(R.id.ratenotselbtn)
        ratepage.setOnClickListener({
            val intent = Intent(this, FetchingAct::class.java)
            startActivity(intent)
        })
        var profbtn = findViewById<ImageButton>(R.id.profnotselbtn)
        profbtn.setOnClickListener({
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        })

        var contactpg = findViewById<Button>(R.id.getservicebtn1)
        contactpg.setOnClickListener({
            val intent = Intent(this, GetService::class.java)
            startActivity(intent)
        })


        initView()
        setValuesToViews()





    }



    private fun initView() {

        tvServTitle = findViewById(R.id.tvServiceTitle)

        tvServPrice = findViewById(R.id.tvprice)
        tvFreeName = findViewById(R.id.tvname)



    }

    private fun setValuesToViews() {

        tvServTitle.text = intent.getStringExtra("title")
        //tvServCat.text = intent.getStringExtra("category")
        tvServPrice.text = intent.getStringExtra("category")
        tvFreeName.text = intent.getStringExtra("description")


    }


}