package com.univalle.widgetinventory.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.univalle.widgetinventory.databinding.ProductBinding
import com.univalle.widgetinventory.model.Producto
import com.univalle.widgetinventory.view.viewholder.InventoryViewHolder

class InventoryAdapter (private val listProducts:MutableList<Producto>
): RecyclerView.Adapter<InventoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val binding = ProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InventoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val product = listProducts[position]
        holder.setItemProduct(product)
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }
}