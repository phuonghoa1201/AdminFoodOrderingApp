package com.example.adminfoodorderingapp

import android.R.layout.simple_list_item_1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.adminfoodorderingapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {

    private lateinit var userName : String
    private lateinit var nameOfRestaurant : String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
// intialation FireBase Auth
        auth = Firebase.auth
// intialation FireBase Database
        database = Firebase.database.reference

        binding.signupButton.setOnClickListener {
            // get txt from edit text
            userName = binding.name.text.toString().trim()
            nameOfRestaurant = binding.restaurantName.text.toString().trim()
            email = binding.emailOrPhone.text.toString().trim()
            password = binding.password.text.toString().trim()
            startActivity(intent)

            if (userName.isBlank() || nameOfRestaurant.isBlank()||email.isBlank()||password.isBlank()){
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }else{
                signUp(email,password)
            }

        }
        binding.haveAccount.setOnClickListener {
            val intent = Intent(this, com.example.adminfoodorderingapp.LoginActivity::class.java)
            startActivity(intent)
        }
        val locationList = arrayOf("Danang", "Saigon", "Hanoi", "HaLong")
        val adapter = ArrayAdapter(this, simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)

    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Account created successfully", Toast.LENGTH_SHORT ).show()
                saveUserData()
                val intent = Intent(this, com.example.adminfoodorderingapp.LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Account creation failed ", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }

    }

    private fun saveUserData() {
        userName = binding.name.text.toString().trim()
        nameOfRestaurant = binding.restaurantName.text.toString().trim()
        email = binding.emailOrPhone.text.toString().trim()
        password = binding.password.text.toString().trim()
        val user
    }
}