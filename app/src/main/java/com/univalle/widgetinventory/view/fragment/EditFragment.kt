package com.univalle.widgetinventory.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.widgetinventory.R
import com.univalle.widgetinventory.databinding.FragmentEditBinding
import com.univalle.widgetinventory.model.Producto

class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        establecerProducto()
        listarProducto()
        setupEdit()
    }

    private fun setupToolbar(){
        binding.contentToolbar.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_editFragment_to_detailFragment)
    }

    private fun setupEdit() {
        binding.btnEdit.setOnClickListener {
            val codigo = binding.tvId.text.toString().substring(4)
            val nombre = binding.etNombreArticulo.text.toString()
            val precio = binding.etPrecio.text.toString()
            val cantidad = binding.etCantidad.text.toString()

            if (nombre.isNotEmpty() && precio.isNotEmpty() && cantidad.isNotEmpty()) {
                val datosActualizados = hashMapOf<String, Any>(
                    "nombre" to nombre,
                    "precio" to precio.toInt(),
                    "cantidad" to cantidad.toInt()
                )

                db.collection("producto").document(codigo).update(datosActualizados)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Articulo editado", Toast.LENGTH_SHORT).show()
                        listarProducto()
                        findNavController().navigate(R.id.action_editFragment_to_inventoryFragment)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Error al editar el artículo: $e", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Llene los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun establecerProducto(){
        db.collection("producto").get().addOnSuccessListener {
            binding.tvId.text = "Id: ${it.documents[0].get("codigo")}"
            binding.etNombreArticulo.setText("${it.documents[0].get("nombre")}")
            binding.etPrecio.setText("${it.documents[0].get("precio")}")
            binding.etCantidad.setText("${it.documents[0].get("cantidad")}")
        }
    }

    private fun listarProducto(){
        db.collection("producto").get().addOnSuccessListener {

            var data = ""
            for (document in it.documents) {
                data += "Codigo: ${document.get("codigo")} " +
                        "Nombre: ${document.get("nombre")} " +
                        "Precio: ${document.get("precio")} " +
                        "Cantidad: ${document.get("cantidad")}\n\n"
            }
            binding.tvListPrueba.text = data

        }
    }

}