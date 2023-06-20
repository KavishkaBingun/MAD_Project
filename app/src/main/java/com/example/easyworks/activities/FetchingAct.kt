package com.example.easyworks.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyworks.adapters.CusAdapter
import com.example.easyworks.models.CustomerModel
import com.example.easyworkscrud.R
import com.google.firebase.database.*

class FetchingAct : AppCompatActivity() {
    private lateinit var cusRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var cusList: ArrayList<CustomerModel>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching1)

        val home = findViewById<ImageButton>(R.id.homenotselserv)
        home.setOnClickListener {
            val intent = Intent(this, ViewServices::class.java)
            startActivity(intent)
        }
        val servicebutton = findViewById<ImageButton>(R.id.serviceselserv)
        servicebutton.setOnClickListener {
            val intent = Intent(this, addService::class.java)
            startActivity(intent)
        }
        val profilebutton = findViewById<ImageButton>(R.id.profilenotselserv)
        profilebutton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        val notifybtn = findViewById<ImageButton>(R.id.notifynotselserv)
        notifybtn.setOnClickListener {
            val intent = Intent(this, FetchAllNotifications::class.java)
            startActivity(intent)
        }


        cusRecyclerView = findViewById(R.id.rvCu)
        cusRecyclerView.layoutManager = LinearLayoutManager(this)
        cusRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingDat)

        cusList = arrayListOf<CustomerModel>()

        getCustomersData()

    }
    private fun getCustomersData() {

        cusRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Customers")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cusList.clear()
                if (snapshot.exists()){
                    for (cusSnap in snapshot.children){
                        val cusData = cusSnap.getValue(CustomerModel::class.java)
                        cusList.add(cusData!!)
                    }
                    val mAdapter = CusAdapter(cusList)
                    cusRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : CusAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingAct, CustomerDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("cusId",cusList[position].cusId)
                            intent.putExtra("cusName",cusList[position].cusName)
                            intent.putExtra("cusEmail",cusList[position].cusEmail)
                            intent.putExtra("cusReview",cusList[position].cusReview)
                            startActivity(intent)
                        }

                    })


                    cusRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}