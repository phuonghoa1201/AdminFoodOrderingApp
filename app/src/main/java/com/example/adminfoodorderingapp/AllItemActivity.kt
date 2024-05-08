package com.example.adminfoodorderingapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodorderingapp.adapter.MenuItemAdapter
import com.example.adminfoodorderingapp.databinding.ActivityAllItemBinding
import com.example.adminfoodorderingapp.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {
    private lateinit var databaseReference :DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems: ArrayList<AllMenu> = ArrayList()
    private val binding:ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//      Retrieve Items from firebase
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()


    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
//        fetch data from data base
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                Clear existing data before populating
                menuItems.clear()
//                loop for through each food item
                for(foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let{
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","Error:${error.message}")
            }

        })
    }
    private fun setAdapter() {

        val adapter = MenuItemAdapter(this@AllItemActivity,menuItems,databaseReference){position ->
            deleteMenuItems(position)
        }
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = adapter
    }

//    private fun deleteMenuItems(position: Int) {
//        val menuItemToDelete = menuItems[position]
//        val menuItemKey = menuItemToDelete.key
//        val foodMenuReference = database.reference.child("menu").child(menuItemKey!!)
//        foodMenuReference.removeValue().addOnCompleteListener { task ->
//            if (task.isSuccessful){
//                menuItems.removeAt(position)
//                binding.menuRecyclerView.adapter?.notifyItemRemoved(position)
//            }else{
//                Toast.makeText(this, "Item no deleted", Toast.LENGTH_SHORT).show()
//            }
//        }
private fun deleteMenuItems(position: Int) {
    if (position >= 0 && position < menuItems.size) {
        val menuItemToDelete = menuItems[position]
        menuItemToDelete?.let {
            val menuItemKey = it.key
            menuItemKey?.let { key ->
                val foodMenuReference = database.reference.child("menu").child(key)
                foodMenuReference.removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        menuItems.removeAt(position)
                        binding.menuRecyclerView.adapter?.notifyItemRemoved(position)
                    } else {
                        Toast.makeText(this, "Item not deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                Toast.makeText(this, "Item key is null", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this, "Item is null", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(this, "Invalid position", Toast.LENGTH_SHORT).show()
    }
}


}
