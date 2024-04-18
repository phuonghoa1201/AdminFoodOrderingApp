package com.example.adminfoodorderingapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminfoodorderingapp.databinding.ItemItemBinding
import com.example.adminfoodorderingapp.model.AllMenu
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context: Context,
    private val menuList:ArrayList<AllMenu>,
    databaseReference: DatabaseReference
//    private val menuItemName:ArrayList<String>, private val menuItemPrice:ArrayList<String>, private val menuItemImage:ArrayList<Int>
):RecyclerView.Adapter<MenuItemAdapter.ViewHolder>(){
    private val itemQuanties = IntArray(menuList.size){1}
    inner class ViewHolder(private val binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
//                val quantity = itemQuanties[position]
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)

                foodNameTv.text = menuItem.foodName
                priceTv.text = menuItem.foodPrice
                Glide.with(context).load(uri).into(foodImg)
//                quantityTv.text = quantity.toString()

                minusBtn.setOnClickListener {
                    decreaseQuantity(position)

                }
                plusBtn.setOnClickListener {
                    increaseQuantity(position)

                }
                deleteBtn.setOnClickListener {
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION){
                        delItem(itemPosition)
                    }
            }
            }
        }
        private fun decreaseQuantity(position:Int){
            if(itemQuanties[position]>1){
                itemQuanties[position]--
                binding.quantityTv.text = itemQuanties[position].toString()
            }
        }
        private fun increaseQuantity(position:Int){
            if(itemQuanties[position]<10){
                itemQuanties[position]++
                binding.quantityTv.text = itemQuanties[position].toString()
            }
        }
        private fun delItem(position:Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = menuList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

}