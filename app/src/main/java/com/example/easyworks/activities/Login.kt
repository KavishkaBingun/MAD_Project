package com.example.easyworks.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.example.easyworkscrud.databinding.LoginpageBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: LoginpageBinding
    private lateinit var firebaseAuth: FirebaseAuth


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textcreate.setOnClickListener {
            val intend = Intent(this, TestSingin::class.java)
            startActivity(intend)
        }

        binding.loginbutton.setOnClickListener {
            val email = binding.editTextTextEmailAddress2.text.toString()
            val pass = binding.editTextTextPassword.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()){


                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, ViewServices::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }


                } else {
                Toast.makeText(this, "Empty field are not allowed", Toast.LENGTH_SHORT).show()
            }

        }
    }




}
