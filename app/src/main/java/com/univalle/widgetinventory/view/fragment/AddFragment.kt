package com.univalle.widgetinventory.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.widgetinventory.R
import com.univalle.widgetinventory.databinding.FragmentAddBinding
import com.univalle.widgetinventory.model.Producto
import com.univalle.widgetinventory.repository.InventoryRepository
import com.univalle.widgetinventory.viewmodel.InventoryViewModel


class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val inventoryViewModel : InventoryViewModel by viewModels()

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
        binding.contentToolbar.toolbar.findViewById<TextView>(R.id.titulo).text = resources.getString(R.string.add_tb)
        binding.etCodigoProducto.addTextChangedListener(textWatcher);
        binding.etNombreArticulo.addTextChangedListener(textWatcher);
        binding.etPrecio.addTextChangedListener(textWatcher);
        binding.etCantidad.addTextChangedListener(textWatcher);
        binding.contentToolbar.toolbar.setNavigationOnClickListener {

            findNavController().navigate(R.id.action_addFragment_to_inventoryFragment)
        }
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
        inventoryViewModel.guardarProducto(codigo,nombre,precio,cantidad)
        findNavController().navigate(R.id.action_addFragment_to_inventoryFragment)
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
