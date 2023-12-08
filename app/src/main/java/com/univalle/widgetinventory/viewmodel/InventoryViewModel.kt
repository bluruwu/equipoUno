package com.univalle.widgetinventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.widgetinventory.model.Producto
import com.univalle.widgetinventory.repository.InventoryRepository
import kotlinx.coroutines.launch

class InventoryViewModel: ViewModel() {
    private val repository = InventoryRepository()
    fun guardarProducto(codigo: String, nombre: String, precio: String, cantidad: String) {
        if (codigo.isNotEmpty() && nombre.isNotEmpty() && precio.isNotEmpty()) {
            viewModelScope.launch {
                val producto = Producto(codigo.toInt(), nombre, precio.toInt(), cantidad.toInt())
                repository.guardarProducto(producto)
            }
        } else {

        }
    }
}