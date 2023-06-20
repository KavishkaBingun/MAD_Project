package com.example.easyworks.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.example.easyworks.models.CustomerModel
import com.example.easyworkscrud.R
import com.google.firebase.database.FirebaseDatabase

class CustomerDetailsActivity : AppCompatActivity() {
    private lateinit var tvCusId: TextView
    private lateinit var tvCusName: TextView
    private lateinit var tvCusEmail: TextView
    private lateinit var tvCusReview: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("cusId").toString(),
                intent.getStringExtra("cusName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("cusId").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Customers").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Customer data Deleted", Toast.LENGTH_LONG).show()
            //after delete redirected to fetching activity
            val intent = Intent(this, FetchingAct::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error->
            Toast.makeText(this, "Deleting err ${error.message}", Toast.LENGTH_LONG).show()

        }
    }

    private fun initView() {
        tvCusId = findViewById(R.id.tvCusId)
        tvCusName = findViewById(R.id.tvCusName)
        tvCusEmail = findViewById(R.id.tvCusEmail)
        tvCusReview = findViewById(R.id.tvCusReview)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvCusId.text = intent.getStringExtra("cusId")
        tvCusName.text = intent.getStringExtra("cusName")
        tvCusEmail.text = intent.getStringExtra("cusEmail")
        tvCusReview.text = intent.getStringExtra("cusReview")

    }

    private fun openUpdateDialog(
        cusId : String,
        cusName : String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etCusName= mDialogView.findViewById<EditText>(R.id.etCusName)
        val etCusEmail= mDialogView.findViewById<EditText>(R.id.etCusEmail)
        val etCusReview= mDialogView.findViewById<EditText>(R.id.etCusReview)
        val btnUpdateData= mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etCusName.setText(intent.getStringExtra("cusName").toString())
        etCusEmail.setText(intent.getStringExtra("cusEmail").toString())
        etCusReview.setText(intent.getStringExtra("cusReview").toString())

        mDialog.setTitle("Updating $cusName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateCusData(
                cusId,
                etCusName.text.toString(),
                etCusEmail.text.toString(),
                etCusReview.text.toString()
            )
            Toast.makeText(applicationContext, "Customer data updated",Toast.LENGTH_LONG).show()

            //set updated data to textviews
            tvCusName.text = etCusName.text.toString()
            tvCusEmail.text = etCusEmail.text.toString()
            tvCusReview.text =  etCusReview.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateCusData(
        id:String,
        name:String,
        email:String,
        review:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Customers").child(id)
        val cusInfo = CustomerModel(id,name,email,review)
        dbRef.setValue(cusInfo)
    }
}

