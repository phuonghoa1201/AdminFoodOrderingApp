// MainActivity.kt
package com.example.adminfoodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Sms.Intents
import com.example.adminfoodorderingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.addMenu.setOnClickListener{
            val intent = Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.allItemMenu.setOnClickListener{
            val intent = Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }
    }
}
