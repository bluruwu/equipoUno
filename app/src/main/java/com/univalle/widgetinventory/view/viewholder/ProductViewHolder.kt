package com.univalle.widgetinventory.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.univalle.widgetinventory.databinding.ProductBinding
import com.univalle.widgetinventory.model.Producto

class ProductViewHolder (binding: ProductBinding): RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    fun setItemProduct(product: Producto){
        bindingItem.productId.text = product.codigo.toString()
        bindingItem.productName.text = product.nombre
        bindingItem.productPrice.text = product.precio.toString()
    }

}