package com.example.adminfoodorderingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodorderingapp.adapter.OrderDetailsAdapter
import com.example.adminfoodorderingapp.databinding.ActivityOrderDetailsBinding
import com.example.adminfoodorderingapp.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding: ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var username : String?= null
    private var address : String?= null
    private var phoneNumber : String?= null
    private var totalPrice : String?= null
    private var foodNames : ArrayList<String> = arrayListOf()
    private var foodImages : ArrayList<String> = arrayListOf()
    private var foodQuantity : ArrayList<Int> = arrayListOf()
    private var foodPrices : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }
        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val receivedOrderDetails = intent.getSerializableExtra("userOrderDetails") as OrderDetails
        receivedOrderDetails?.let {
            orderDetails ->
//        }
//        if(receivedOrderDetails != null) {
            username = receivedOrderDetails.userName
            foodNames = receivedOrderDetails.foodNames as ArrayList<String>
            foodImages = receivedOrderDetails.foodImages as ArrayList<String>
            foodQuantity = receivedOrderDetails.foodQuantities as ArrayList<Int>
            address = receivedOrderDetails.address
            phoneNumber = receivedOrderDetails.phoneNumber
            foodPrices = receivedOrderDetails.foodPrices as ArrayList<String>
            totalPrice = receivedOrderDetails.totalPrice
            setUserDetail()
            setAdapter()
        }

    }

    private fun setUserDetail() {
        binding.name.text = username
        binding.address.text = address
        binding.phone.text = phoneNumber
        binding.totalPay.text = totalPrice

    }

    private fun setAdapter() {
        binding.orderDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter  = OrderDetailsAdapter(this, foodNames,foodImages,foodQuantity,foodPrices)
        binding.orderDetailRecyclerView.adapter = adapter
    }

}