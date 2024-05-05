package com.example.adminfoodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminfoodorderingapp.databinding.ActivityAdminProfileBinding


class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.nameTextView.isEnabled=false
        binding.editTextAddress.isEnabled=false
        binding.editTextEmail.isEnabled=false
        binding.editTextPhone.isEnabled=false
        binding.editPassword.isEnabled=false

        var isEnable = false
        binding.editButton.setOnClickListener{
            isEnable = ! isEnable
            binding.nameTextView.isEnabled=isEnable
            binding.editTextAddress.isEnabled=isEnable
            binding.editTextEmail.isEnabled=isEnable
            binding.editTextPhone.isEnabled=isEnable
            binding.editPassword.isEnabled=isEnable
            if (isEnable){
                binding.nameTextView.requestFocus()
            }
        }

    }
}