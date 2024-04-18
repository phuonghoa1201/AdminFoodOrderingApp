package com.example.adminfoodorderingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodorderingapp.adapter.AddItemAdapter
import com.example.adminfoodorderingapp.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding:ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val menuFoodName = listOf("Burger","Hot dog","Milkshake","Fries","Taco","Pizza","Fried chicken")
        val menuFoodPrice = listOf("$5","$7","$8","$9","$4","$5.5","$6.5")
        val menuFoodImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu1
        )
        val adapter = AddItemAdapter( ArrayList(menuFoodName),ArrayList(menuFoodPrice), ArrayList( menuFoodImage))

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = adapter

    }
}