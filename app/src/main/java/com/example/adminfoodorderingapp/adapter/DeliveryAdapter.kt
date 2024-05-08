package com.example.adminfoodorderingapp.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodorderingapp.databinding.DeliveryItemBinding

class DeliveryAdapter(private val customerNames: MutableList<String>, private val statusMoney: MutableList<Boolean>) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size

    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                // Sử dụng biến binding từ itemView để truy cập các thành phần trong layout item
                customerName.text = customerNames[position]
//                moneyStatus.text = statusMoney[position] // Sử dụng ID moneyStatus để tham chiếu đến TextView trong layout
                if (statusMoney[position] == true){
                    moneyStatus.text = "Received"
                }else{
                    moneyStatus.text = "Not Received"
                }
                val colorMap = mapOf(
                    true to Color.GREEN, false to Color.RED //, "pending" to Color.GRAY
                )
                moneyStatus.setTextColor(colorMap[statusMoney[position]] ?: Color.BLACK)
                StatusColor.backgroundTintList = ColorStateList.valueOf(colorMap[statusMoney[position]]?:Color.BLACK)
            }
        }
    }
}
