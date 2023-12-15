package com.univalle.widgetinventory.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
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


    suspend fun getListProducts(): MutableList<Producto> {
        val productList = mutableListOf<Producto>()

        try {
            val querySnapshot = db.collection("productos").get().await()

            for (document in querySnapshot.documents) {
                val producto = Producto(
                    document.getLong("codigo")?.toInt() ?: 0,
                    document.getString("nombre") ?: "",
                    document.getDouble("precio")?.toFloat() ?: 0.0f,
                    document.getLong("cantidad")?.toInt() ?: 0
                )

                productList.add(producto)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return productList
    }


}