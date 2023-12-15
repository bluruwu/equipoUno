package com.univalle.widgetinventory.view.fragment
import android.util.Log;
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.widgetinventory.R
import com.univalle.widgetinventory.databinding.FragmentEditBinding
import com.univalle.widgetinventory.model.Producto
import com.univalle.widgetinventory.viewmodel.InventoryViewModel

class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val db = FirebaseFirestore.getInstance()
    private val inventoryViewModel : InventoryViewModel by viewModels()

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
        val codigo = 1223

        binding.etNombreArticulo.addTextChangedListener(textWatcher);
        binding.etPrecio.addTextChangedListener(textWatcher);
        binding.etCantidad.addTextChangedListener(textWatcher);

        setupToolbar()
        establecerProducto(codigo)
        listarProducto()
        setupEdit()
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            val allFieldsFilled = !binding.etNombreArticulo.text.toString().isEmpty() &&
                    !binding.etPrecio.text.toString().isEmpty() &&
                    !binding.etCantidad.text.toString().isEmpty()

            cambiarEstadoBoton(allFieldsFilled)
        }
    }

    private fun cambiarEstadoBoton(habilitar: Boolean) {
        if (habilitar) {
            val colorTextoNormal = ContextCompat.getColor(requireContext(), R.color.white)
            binding.btnEdit.isEnabled = true
            binding.btnEdit.setTextColor(colorTextoNormal)
        } else {
            val colorInhabilitado = ContextCompat.getColor(requireContext(), R.color.grayH2C11)
            binding.btnEdit.isEnabled = false
            binding.btnEdit.setTextColor(colorInhabilitado)
        }
    }

    private fun setupToolbar(){
        binding.contentToolbar.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_editFragment_to_detailFragment)
    }

    private fun setupEdit() {
        Log.i("setupEdit", "parece que no se")
        binding.btnEdit.setOnClickListener {
            val codigo = binding.tvId.text.toString().substring(4)
            val nombre = binding.etNombreArticulo.text.toString()
            val precio = binding.etPrecio.text.toString()
            val cantidad = binding.etCantidad.text.toString()

            inventoryViewModel.editarProducto(codigo, nombre, precio, cantidad)
            listarProducto()
        }
    }

    private fun establecerProducto(codigo: Int){

        inventoryViewModel.obtenerProducto(codigo)

        inventoryViewModel.producto.observe(viewLifecycleOwner) { producto ->
            binding.tvId.text = "Id: ${codigo}"
            if (producto != null) {
                binding.etNombreArticulo.setText(producto.nombre)
                binding.etPrecio.setText(producto.precio.toString())
                binding.etCantidad.setText(producto.cantidad.toString())
            }
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