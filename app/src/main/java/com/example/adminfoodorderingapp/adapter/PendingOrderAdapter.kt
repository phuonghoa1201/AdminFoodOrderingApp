package com.example.adminfoodorderingapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminfoodorderingapp.PendingOrderActivity
import com.example.adminfoodorderingapp.databinding.PendingOrdersItemBinding

class PendingOrderAdapter(
    private val clientNames: MutableList<String>,
    private val quantity: MutableList<String>,
    private val foodImage: MutableList<String>,
    private val context: Context,
    private val itemClicked: PendingOrderActivity
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {
        interface OnItemClicked{
            fun onItemClickListener(position: Int)
            fun onItemAcceptListener(position: Int)
            fun onItemDispatchListener(position: Int)

        }

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
//                orderedFoodImage.setImageResource(foodImage[position])
                var uriString = foodImage[position]
                var uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(orderedFoodImage)

                orderedAcceptButton.apply {
                    text = if (isAccepted) "Dispatch" else "Accept"
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is accecpted ")
                            itemClicked.onItemAcceptListener(position)
                        } else {
//                            cusomerName = clientName
                            clientNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is dispatched")
                            itemClicked.onItemDispatchListener(position)
                        }
                    }
                }
                // xu ly click vao 1 item
                itemView.setOnClickListener {
                    itemClicked.onItemClickListener(position)
                }
            }
        }
        private fun showToast(message: String){
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        }
    }
}
