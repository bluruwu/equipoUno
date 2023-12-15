package com.univalle.widgetinventory.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.univalle.widgetinventory.databinding.ProductBinding
import com.univalle.widgetinventory.model.Producto
import java.lang.String

class InventoryViewHolder (binding: ProductBinding): RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding

    fun setItemProduct(product: Producto){
        val numeroFormateado = String.format("%.2f", product.precio)
        bindingItem.productId.text = product.codigo.toString()
        bindingItem.productName.text = product.nombre
        bindingItem.productPrice.text = numeroFormateado
    }

}