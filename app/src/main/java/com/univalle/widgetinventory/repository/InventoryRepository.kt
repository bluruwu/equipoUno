package com.univalle.widgetinventory.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
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

    suspend fun editarProducto(codigo: String, producto: Producto) {
        db.collection("producto").document(codigo).update(
            hashMapOf<String, Any> (
                "nombre" to producto.nombre,
                "precio" to producto.precio,
                "cantidad" to producto.cantidad
            )
        ).await()
    }

    suspend fun obtenerProducto(codigo: Int): DocumentSnapshot? {
        return try {
            db.collection("producto").document(codigo.toString()).get().await()
        } catch (e: Exception) {
            null
        }
    }

}