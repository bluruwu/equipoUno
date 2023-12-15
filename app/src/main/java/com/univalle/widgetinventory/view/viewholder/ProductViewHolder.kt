package com.univalle.widgetinventory.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.univalle.widgetinventory.databinding.ProductBinding
import com.univalle.widgetinventory.model.Producto

class ProductViewHolder (binding: ProductBinding): RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding

    fun setItemProduct(producto: Producto, onProductClickListener: (Int, String) -> Unit){

    }
}