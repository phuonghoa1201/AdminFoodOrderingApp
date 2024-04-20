package com.example.adminfoodorderingapp

import com.example.adminfoodorderingapp.model.UserModel
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var nameOfRestaurant: String
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

        // Khởi tạo Firebase Auth
        auth = Firebase.auth

        // Khởi tạo Firebase Database
        database = Firebase.database.reference

        binding.signupButton.setOnClickListener {
            // Lấy dữ liệu từ các trường EditText
            userName = binding.name.text.toString().trim()
            nameOfRestaurant = binding.restaurantName.text.toString().trim()
            email = binding.emailOrPhone.text.toString().trim()
            password = binding.password.text.toString().trim()


            if (userName.isBlank() || nameOfRestaurant.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }
        }

        binding.haveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val locationList = arrayOf("Danang", "Saigon", "Hanoi", "HaLong")
        val adapter = ArrayAdapter(this, simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Account creation failed ", Toast.LENGTH_SHORT).show()
                Log.e("com.example.adminfoodorderingapp.SignUpActivity", "Account creation failed", task.exception)
            }
        }
    }

    private fun saveUserData() {
        userName = binding.name.text.toString().trim()
        nameOfRestaurant = binding.restaurantName.text.toString().trim()
        email = binding.emailOrPhone.text.toString().trim()
        password = binding.password.text.toString().trim()
        val user = UserModel(userName, nameOfRestaurant, email, password)
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        // Thêm log để xác minh rằng hàm được gọi và dữ liệu được ghi vào Realtime Database
        Log.d("com.example.adminfoodorderingapp.SignUpActivity", "Saving user data...")

        val userRef = database.child("user").child(userId) // Tạo một tham chiếu đến nút user có ID là userId
        userRef.setValue(user) // Ghi dữ liệu của user vào nút user tương ứng

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Xử lý sự kiện khi dữ liệu đã được ghi thành công
                Log.d("com.example.adminfoodorderingapp.SignUpActivity", "User data saved successfully")
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý sự kiện khi ghi dữ liệu thất bại
                Log.e("com.example.adminfoodorderingapp.SignUpActivity", "Failed to save user data", error.toException())
            }
        })
    }

}
