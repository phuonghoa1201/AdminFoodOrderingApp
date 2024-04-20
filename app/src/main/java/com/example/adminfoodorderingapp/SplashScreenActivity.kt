// SplashScreenActivity.kt
package com.example.adminfoodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.adminfoodorderingapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Delay 3 seconds before moving to LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, com.example.adminfoodorderingapp.LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish Splash Screen Activity
        }, 6000)
    }
}
