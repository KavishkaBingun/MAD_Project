package com.example.easyworks.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyworks.adapters.EmpAdapter
import com.example.easyworks.models.Users
import com.example.easyworkscrud.R
import com.google.firebase.database.*

class FetActivity1 : AppCompatActivity() {
    private lateinit var empRecyclerview : RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<Users>
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fetchingview1)


        val home = findViewById<ImageButton>(R.id.homenotselser)
        home.setOnClickListener {
            val intent = Intent(this, ViewServices::class.java)
            startActivity(intent)
        }
        val servicebutton = findViewById<ImageButton>(R.id.serviceselser)
        servicebutton.setOnClickListener {
            val intent = Intent(this, addService::class.java)
            startActivity(intent)
        }
        val ratingbutton = findViewById<ImageButton>(R.id.ratingnotselser)
        ratingbutton.setOnClickListener {
            val intent = Intent(this, FetchingAct::class.java)
            startActivity(intent)
        }
        val profilebutton = findViewById<ImageButton>(R.id.profilenotselserv)
        profilebutton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }



        empRecyclerview = findViewById(R.id.empfet)
        empRecyclerview.layoutManager = LinearLayoutManager(this)
        empRecyclerview.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.LoadingDatafet)

        empList = arrayListOf<Users>()

        getEmployeeData()
    }
    private fun getEmployeeData(){
        empRecyclerview.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("users")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if(snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(Users::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter = EmpAdapter(empList)
                    empRecyclerview.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetActivity1, Accountsettings::class.java)

                            //put extras
                            //intent.putExtra("uid",empList[position].uid)
                            intent.putExtra("uid",empList[position].uid)
                            intent.putExtra("firstname",empList[position].firstname)
                            intent.putExtra("lastname",empList[position].lastname)
                            intent.putExtra("address",empList[position].address)
                            intent.putExtra("phone",empList[position].phone)
                            intent.putExtra("email",empList[position].email)
                            startActivity(intent)

                        }
                    })

                    empRecyclerview.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}