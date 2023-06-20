package com.example.easyworks.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.easyworks.models.ServiceModel
import com.example.easyworkscrud.R
import com.google.firebase.database.FirebaseDatabase


private lateinit var tvSerId: TextView
      private lateinit var tvServTitle: TextView
     // private lateinit var tvServCat: TextView
      private lateinit var tvServPrice: TextView
      private lateinit var tvFreeName : TextView
      private lateinit var tvFreeContatc : TextView
      private lateinit var tvFreeEmail :TextView
      private lateinit var btnUpdate: Button
      private lateinit var btnDelete: Button

    class ServiceDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_service_details)

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
        var profilebtn = findViewById<ImageButton>(R.id.profnotselbtn)
        profilebtn.setOnClickListener({
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        })
        var ratebtn = findViewById<ImageButton>(R.id.ratenotselbtn)
        ratebtn.setOnClickListener({
            val intent = Intent(this, FetchingAct::class.java)
            startActivity(intent)
        })

        var ratepagebtn = findViewById<TextView>(R.id.tvrate)
        ratepagebtn.setOnClickListener({
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        })






      initView()
      setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("serviceid").toString(),
                intent.getStringExtra("title").toString()

            )
        }

        btnDelete.setOnClickListener{
            deleteRecors(
                intent.getStringExtra("serviceid").toString()
            )
        }



}

        private fun deleteRecors(
            serviceid: String

        ){
            val dbref = FirebaseDatabase.getInstance().getReference("Services").child(serviceid)
            val sTask = dbref.removeValue()

            sTask.addOnSuccessListener{
                Toast.makeText(this,"Service data deleated", Toast.LENGTH_LONG).show()

                //navigate to view records page after deleting record
                val intent = Intent(this, ViewServices::class.java)
                finish()
                startActivity(intent)
            }.addOnFailureListener{ error ->

                Toast.makeText(this,"Deleting err ${error.message}", Toast.LENGTH_LONG).show()

            }

        }

        private fun initView() {
       tvSerId = findViewById(R.id.tvServicid)
       tvServTitle = findViewById(R.id.tvServiceTitle)
      // tvServCat = findViewById(R.id.tvcategory)
       tvServPrice = findViewById(R.id.tvprice)
       tvFreeName = findViewById(R.id.tvname)
       tvFreeContatc = findViewById(R.id.tvcontact)
       tvFreeEmail = findViewById(R.id.tvemail)

       btnUpdate = findViewById(R.id.btnUpdate)
       btnDelete = findViewById(R.id.btnDelete)
}

    private fun setValuesToViews() {
      tvSerId.text = intent.getStringExtra("serviceid")
      tvServTitle.text = intent.getStringExtra("title")
      //tvServCat.text = intent.getStringExtra("category")
      tvServPrice.text = intent.getStringExtra("price")
      tvFreeName.text = intent.getStringExtra("name")
      tvFreeContatc.text = intent.getStringExtra("phone")
      tvFreeEmail.text = intent.getStringExtra("email")

}
        private fun openUpdateDialog(
            serviceid : String,
            name : String
        ) {
            val sDialog = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val sDialogView = inflater.inflate(R.layout.update_dialog2,null)

            sDialog.setView(sDialogView)

            val etServTitle = sDialogView.findViewById<EditText>(R.id.etservTitle)
            //val etServCat = sDialogView.findViewById<Spinner>(R.id.etservcat)
            val etPrice = sDialogView.findViewById<EditText>(R.id.etPrice)
            val etFreeName = sDialogView.findViewById<EditText>(R.id.etFreName)
            val etContact = sDialogView.findViewById<EditText>(R.id.etFreCon)
            val etEmail = sDialogView.findViewById<EditText>(R.id.etFreEmail)
            val btnUpdateData = sDialogView.findViewById<Button>(R.id.btnUpdateData)

            etServTitle.setText(intent.getStringExtra("title").toString())
           // etServCat.setText(intent.getStringExtra("category").toString())
            etPrice.setText(intent.getStringExtra("price").toString())
            etFreeName.setText(intent.getStringExtra("name").toString())
            etContact.setText(intent.getStringExtra("phone").toString())
            etEmail.setText(intent.getStringExtra("email").toString())

            sDialog.setTitle("Updating $name record")

            val alertDialog = sDialog.create()
            alertDialog.show()

            btnUpdateData.setOnClickListener{
                updateServiceData(
                    serviceid,
                    etServTitle.text.toString(),
                    etPrice.text.toString(),
                    etFreeName.text.toString(),
                    etContact.text.toString(),
                    etEmail.text.toString()
                )

                Toast.makeText(applicationContext,"Service Data Updated",Toast.LENGTH_LONG).show()
                //updating data to new text views
                tvServTitle.text =   etServTitle.text.toString()
                //tvServCat.text = intent.getStringExtra("category")
                tvServPrice.text = etPrice.text.toString()
                tvFreeName.text = etFreeName.text.toString()
                tvFreeContatc.text = etContact.text.toString()
                tvFreeEmail.text =  etEmail.text.toString()

                alertDialog.dismiss()
            }




        }

        private fun updateServiceData(
            serviceid: String,
            title: String,
            price : String,
            name: String,
            phone :String,
            email : String

        ){
            val dbref = FirebaseDatabase.getInstance().getReference("Services").child(serviceid)
            val serviceInfo = ServiceModel(serviceid,title,price,name,phone,email)
            dbref.setValue(serviceInfo)

        }
    }