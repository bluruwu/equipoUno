package com.univalle.widgetinventory.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.widgetinventory.model.Producto
import kotlinx.coroutines.tasks.await

class InventoryRepository {
    private val db = FirebaseFirestore.getInstance()
    suspend fun guardarProducto(producto: Producto) {
        db.collection("producto").document(producto.codigo.toString()).set(
            hashMapOf(
                "codigo" to producto.codigo,
                "nombre" to producto.nombre,
                "precio" to producto.precio,
                "cantidad" to  producto.cantidad
            )
        ).await()
    }
}