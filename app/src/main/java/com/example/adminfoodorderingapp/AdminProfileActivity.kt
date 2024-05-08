package com.example.adminfoodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminfoodorderingapp.databinding.ActivityAdminProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Khởi tạo đối tượng FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Khởi tạo đối tượng FirebaseDatabase
        database = FirebaseDatabase.getInstance()

        // Tham chiếu đến node "user" trong cơ sở dữ liệu
        adminReference = database.reference.child("user")

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.owername.isEnabled=false
        binding.email.isEnabled=false
        binding.pwd.isEnabled=false
        binding.phone.isEnabled=false
        binding.address.isEnabled=false
        binding.saveInfoButton.isEnabled = false

        var isEnable = false
        binding.editButton.setOnClickListener{
            isEnable = ! isEnable
            binding.owername.isEnabled=isEnable
            binding.email.isEnabled=isEnable
            binding.pwd.isEnabled=isEnable
            binding.phone.isEnabled=isEnable
            binding.address.isEnabled=isEnable
            if (isEnable){
                binding.owername.requestFocus()
            }
        }
        retrieveUserData()

    }

    private fun retrieveUserData() {
        val currentUserUid = auth.currentUser?.uid
        if(currentUserUid != null){
            adminReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var ownerName = snapshot.child("name").getValue()
                        var email = snapshot.child("email").getValue()
                        var password = snapshot.child("password").getValue()
                        var address = snapshot.child("address").getValue()
                        var phone = snapshot.child("phone").getValue()
                        setDataToTextView(ownerName, email, password, address, phone)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?

    ){
        binding.owername.setText(ownerName.toString())
        binding.email.setText(email.toString())
        binding.pwd.setText(password.toString())
        binding.phone.setText(phone.toString())
        binding.address.setText(address.toString())
    }
}