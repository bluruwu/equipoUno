package com.univalle.widgetinventory.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.widgetinventory.R
import com.univalle.widgetinventory.databinding.FragmentAddBinding
import com.univalle.widgetinventory.model.Producto


class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setup()
        binding.etCodigoProducto.addTextChangedListener(textWatcher);
        binding.etNombreArticulo.addTextChangedListener(textWatcher);
        binding.etPrecio.addTextChangedListener(textWatcher);
        binding.etCantidad.addTextChangedListener(textWatcher);
        cambiarEstadoBoton(false)
    }
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            val allFieldsFilled = !binding.etCodigoProducto.getText().toString().isEmpty() &&
                    !binding.etNombreArticulo.getText().toString().isEmpty() &&
                    !binding.etPrecio.getText().toString().isEmpty() &&
                    !binding.etCantidad.getText().toString().isEmpty()

            cambiarEstadoBoton(allFieldsFilled)
        }
    }
    private fun guardarProducto() {
        val codigo = binding.etCodigoProducto.text.toString()
        val nombre = binding.etNombreArticulo.text.toString()
        val precio = binding.etPrecio.text.toString()
        val cantidad = binding.etCantidad.text.toString()

        if (codigo.isNotEmpty() && nombre.isNotEmpty() && precio.isNotEmpty()) {
            val producto = Producto(codigo.toInt(), nombre, precio.toInt(), cantidad.toInt())

            db.collection("producto").document(producto.codigo.toString()).set(
                hashMapOf(
                    "codigo" to producto.codigo,
                    "nombre" to producto.nombre,
                    "precio" to producto.precio,
                    "cantidad" to  producto.cantidad
                )

            )

            Toast.makeText(context, "Articulo guardado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Llene los campos", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setup() {
        binding.btnAdd.setOnClickListener {
            guardarProducto()
        }
    }

    private fun cambiarEstadoBoton(habilitar: Boolean) {
        if (habilitar) {
            // Obtener el color de texto normal (cuando el botón está habilitado)
            val colorTextoNormal = ContextCompat.getColor(requireContext(), R.color.white)

            // Habilitar el botón
            binding.btnAdd.isEnabled = true

            // Restaurar el color del texto original
            binding.btnAdd.setTextColor(colorTextoNormal)
        } else {
            // Cambiar el color del texto cuando el botón está deshabilitado
            val colorInhabilitado = ContextCompat.getColor(requireContext(), R.color.grayH2C11)

            // Deshabilitar el botón
            binding.btnAdd.isEnabled = false

            // Cambiar el color del texto cuando el botón está deshabilitado
            binding.btnAdd.setTextColor(colorInhabilitado)
        }
    }
    }
