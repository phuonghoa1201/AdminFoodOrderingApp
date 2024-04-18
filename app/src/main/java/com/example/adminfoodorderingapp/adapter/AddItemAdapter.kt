package com.example.adminfoodorderingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodorderingapp.databinding.ItemItemBinding

class AddItemAdapter(private val menuItemName:ArrayList<String>, private val menuItemPrice:ArrayList<String>, private val menuItemImage:ArrayList<Int>):RecyclerView.Adapter<AddItemAdapter.ViewHolder>(){
    private val itemQuanties = IntArray(menuItemName.size){1}
    inner class ViewHolder(private val binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodName.text = menuItemName[position]
                price.text = menuItemPrice[position]
                foodImg.setImageResource(menuItemImage[position])

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
                binding.quantity.text = itemQuanties[position].toString()
            }
        }
        private fun increaseQuantity(position:Int){
            if(itemQuanties[position]<10){
                itemQuanties[position]++
                binding.quantity.text = itemQuanties[position].toString()
            }
        }
        private fun delItem(position:Int) {
            menuItemName.removeAt(position)
            menuItemPrice.removeAt(position)
            menuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuItemName.size)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = menuItemName.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

}