package com.univalle.widgetinventory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.widgetinventory.model.Producto
import com.univalle.widgetinventory.repository.InventoryRepository
import kotlinx.coroutines.launch

class InventoryViewModel: ViewModel() {
    private val repository = InventoryRepository()
    private val _listProducts = MutableLiveData<MutableList<Producto>>()
    private val _producto = MutableLiveData<Producto>()
    val listProducts: LiveData<MutableList<Producto>> get() = _listProducts
    val producto: LiveData<Producto> get() = _producto
    val listProducts: LiveData<MutableList<Producto>> get() = _listProducts

    fun obtenerProducto(codigo: Int) {
        viewModelScope.launch {
            val documento = repository.obtenerProducto(codigo)
            if (documento != null) {
                val producto = Producto(
                    codigo,
                    documento.getString("nombre") ?: "",
                    documento.getDouble("precio")?.toFloat() ?: 0.0f,
                    documento.getLong("cantidad")?.toInt() ?: 0
                )
                _producto.value = producto
            }
        }
    }
    fun getListProducts(){
        viewModelScope.launch {
            _listProducts.value = repository.getListProducts()
        }
    }

    fun guardarProducto(codigo: String, nombre: String, precio: String, cantidad: String) {
        if (codigo.isNotEmpty() && nombre.isNotEmpty() && precio.isNotEmpty()) {
            viewModelScope.launch {
                val producto = Producto(codigo.toInt(), nombre, precio.toFloat(), cantidad.toInt())
                repository.guardarProducto(producto)
            }
        }
    }

    fun editarProducto(codigo: String, nombre: String, precio: String, cantidad: String) {
        if (nombre.isNotEmpty() && precio.isNotEmpty() && cantidad.isNotEmpty()) {
            viewModelScope.launch {
                val producto = Producto(codigo.toInt(), nombre, precio.toFloat(), cantidad.toInt())
                repository.editarProducto(codigo, producto)
            }
        }
    }
}