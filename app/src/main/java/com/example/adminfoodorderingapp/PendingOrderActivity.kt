package com.example.adminfoodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodorderingapp.adapter.PendingOrderAdapter
import com.example.adminfoodorderingapp.databinding.ActivityPendingOrderBinding
import com.example.adminfoodorderingapp.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity(),PendingOrderAdapter.OnItemClicked {

    private lateinit var binding: ActivityPendingOrderBinding
    private var listOfName: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfImageFirstFoodOrder: MutableList<String> = mutableListOf()
    private var listOfOrderItem: MutableList<OrderDetails> = mutableListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initialization of database
        database = FirebaseDatabase.getInstance()
//        initialization of database Ref
        databaseOrderDetails = database.reference.child("OrderDetails")
        getOrderDetails()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun getOrderDetails() {
//        retrieve order details from firebase database
        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(orderSnapshot in snapshot.children){
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun addDataToListForRecyclerView() {
        for(orderItem in listOfOrderItem ) {
//            add data to respective list for populating the recyclerView
            orderItem.userName?.let{listOfName.add(it)} // them vao ds username
            orderItem.totalPrice?.let{listOfTotalPrice.add(it)} // them vao ds username
            orderItem.foodImages?.filterNot{it.isEmpty()}?.forEach {
                listOfImageFirstFoodOrder.add(it)
            }// them vao ds username

        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(listOfName,listOfTotalPrice,listOfImageFirstFoodOrder,this,this)
        binding.pendingOrderRecyclerView.adapter = adapter
    }

    override fun onItemClickListener(position: Int) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        val userOrderDetails = listOfOrderItem[position]
        intent.putExtra("userOrderDetails",userOrderDetails)
        startActivity(intent)
    }

    override fun onItemAcceptListener(position: Int) {
        //handle item acceptance and update database
        val childItemPushKey = listOfOrderItem[position].itemPushKey
        // return lambda result, context object = it
        val clickItemRef = childItemPushKey?.let {
            database.reference.child("OrderDetails").child(it)
        }
        clickItemRef?.child("orderAccepted")?.setValue(true)
        updateOrderAcceptStatus(position)
    }

    private fun updateOrderAcceptStatus(position: Int) {
        // update order acceptance in user's BuyHistory and OrderDetails
        val userIdOfClickedItem = listOfOrderItem[position].userUid
        val pushKeyOfClickedItem = listOfOrderItem[position].itemPushKey
        val buyHistoryRef = database.reference.child("user").child(userIdOfClickedItem!!).child("BuyHistory").child(pushKeyOfClickedItem!!)
        //update order acceptance in user's BuyHistory
        buyHistoryRef.child("orderAccepted").setValue(true)
        // update order acceptance in user's OrderDetails
        databaseOrderDetails.child(pushKeyOfClickedItem).child("orderAccepted").setValue(true)
    }

    override fun onItemDispatchListener(position: Int) {
        // handle item Dispatch and update database
        val dispatchItemPushKey = listOfOrderItem[position].itemPushKey
        val dispatchItemOrderRef = database.reference.child("CompletedOrder").child(dispatchItemPushKey!!)
        dispatchItemOrderRef.setValue(listOfOrderItem[position]).addOnSuccessListener { 
            deleteItemFromOrderDetails(dispatchItemPushKey)
        }
        // update orderDispatched
        dispatchItemOrderRef.child("orderDispatched").setValue(true)
        val userIdOfClickedItem = listOfOrderItem[position].userUid
        val buyHistoryRef = database.reference.child("user").child(userIdOfClickedItem!!).child("BuyHistory").child(dispatchItemPushKey)
        buyHistoryRef.child("orderDispatched").setValue(true)

    }

    private fun deleteItemFromOrderDetails(dispatchItemPushKey: String) {
        val orderDetailsItemRef =
            database.reference.child("OrderDetails").child(dispatchItemPushKey)
        orderDetailsItemRef.removeValue().addOnSuccessListener {
            Toast.makeText(this, "Item is dispatched", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Item is not dispatched", Toast.LENGTH_SHORT).show()
        }
    }

}
//AcceptedOrder -> orderAccepted
