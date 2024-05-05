package com.example.adminfoodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminfoodorderingapp.databinding.ActivityAdminProfileBinding
import com.example.adminfoodorderingapp.databinding.ActivityCreateUserBinding

class CreateUserActivity : AppCompatActivity() {
    private val binding: ActivityCreateUserBinding by lazy {
        ActivityCreateUserBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}