package com.example.adminfoodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminfoodorderingapp.databinding.ActivityMainBinding
import com.example.adminfoodorderingapp.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database:FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var  completedOrderReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addMenu.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        binding.allItemMenu.setOnClickListener {
            val intent = Intent(this, AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.outForDeliveryButton.setOnClickListener {
            val intent = Intent(this, OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.profile.setOnClickListener {
            val intent = Intent(this, AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.createUser.setOnClickListener {
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }
        binding.pendingOrderTextView.setOnClickListener {
            val intent = Intent(this, PendingOrderActivity::class.java)
            startActivity(intent)
        }
        pendingOrder()
        completedOrders()
        wholeTimeEarning()
    }

    private fun wholeTimeEarning() {
        var listOfTotalPay = mutableListOf<Int>()
        completedOrderReference = FirebaseDatabase.getInstance().reference.child("CompletedOrder")
        completedOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(orderSnapshot in snapshot.children) {
                    var completedOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completedOrder?.totalPrice?.replace("$", "")?.toIntOrNull()
                        ?.let { i ->
                            listOfTotalPay.add(i)
                        }
                }
                binding.wholeTimeEarning.text = listOfTotalPay.sum().toString() + "$"

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
//private fun wholeTimeEarning() {
//    val completedOrderReference = FirebaseDatabase.getInstance().reference.child("CompletedOrder")
//    completedOrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            val listOfTotalPay = mutableListOf<Int>()
//            for (orderSnapshot in snapshot.children) {
//                val completedOrder = orderSnapshot.getValue(OrderDetails::class.java)
//                completedOrder?.let {
//                    it.totalPrice?.replace("$", "")?.toIntOrNull()?.let { totalPrice ->
//                        listOfTotalPay.add(totalPrice)
//                    }
//                }
//            }
//            val totalEarnings = listOfTotalPay.sum()
//            binding.wholeTimeEarning.text = "$totalEarnings"
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            // Handle error
//        }
//    })
//}


    private fun completedOrders() {
        database = FirebaseDatabase.getInstance()
        var completedOrderReference = database.reference.child("CompletedOrder")
        var completedOrderItemCount = 0
        completedOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                completedOrderItemCount = snapshot.childrenCount.toInt()
                binding.completedOrders.text = completedOrderItemCount.toString()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun pendingOrder() {
        database = FirebaseDatabase.getInstance()
        var pendingOrderReference = database.reference.child("OrderDetails")
        var pendingOrderItemCount = 0
        pendingOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingOrders.text = pendingOrderItemCount.toString()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}

