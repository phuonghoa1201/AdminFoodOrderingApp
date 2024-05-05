package com.example.adminfoodorderingapp.adapter

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodorderingapp.databinding.PendingOrdersItemBinding

class PendingOrderAdapter(private val clientNames: ArrayList<String>, private val quantity: ArrayList<String>, private val foodImage: ArrayList<Int>, private val context: Context) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding = PendingOrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = clientNames.size

    inner class PendingOrderViewHolder(private val binding: PendingOrdersItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false

        fun bind(position: Int) {
            binding.apply {
                clientName.text = clientNames[position]
                pendingOrderQuantity.text = quantity[position]
                orderedFoodImage.setImageResource(foodImage[position])

                orderedAcceptButton.apply {
                    text = if (isAccepted) "Dispatch" else "Accept"
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is accecpted ")
                        } else {
//                            cusomerName = clientName
                            clientNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is dispatched")
                        }
                    }
                }
            }
        }
        private fun showToast(message: String){
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        }
    }
}
