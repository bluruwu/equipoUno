package com.univalle.widgetinventory.view.fragment

import InventoryAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.univalle.widgetinventory.R
import com.univalle.widgetinventory.databinding.FragmentInventoryBinding
import com.univalle.widgetinventory.model.Producto
import com.univalle.widgetinventory.view.MainActivity
import com.univalle.widgetinventory.viewmodel.InventoryViewModel

class InventoryHomeFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private var inventoryAdapter: InventoryAdapter? = null
    private lateinit var binding: FragmentInventoryBinding
    private val inventoryViewModel : InventoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventoryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerListProducts()
        setupButton()
    }

    private fun setupButton() {
        binding.contentToolbar.toolbar.setNavigationOnClickListener { onExitPressed() }
        binding.fbagregar.setOnClickListener { findNavController().navigate(R.id.action_inventoryFragment_to_addFragment) }
        binding.contentToolbar.icono.setOnClickListener { onExitPressed() }

    }


    private fun onExitPressed() {
        (requireActivity() as MainActivity).signOffAndRedirectToLogin()
    }

    private fun observerListProducts() {
        inventoryViewModel.getListProducts()
        inventoryViewModel.listProducts.observe(viewLifecycleOwner) { listaProductos ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager

            inventoryAdapter = InventoryAdapter(listaProductos) { selectedProduct ->
                // Handle the selected product here
                onProductSelected(selectedProduct)
            }

            recycler.adapter = inventoryAdapter
        }
    }
    private fun onProductSelected(selectedProduct: Producto) {

        val toastMessage = "Producto seleccionado: ${selectedProduct.codigo}"
        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()

        val productID = selectedProduct.codigo
        val bundle=Bundle()
        bundle.putInt("ProductID",productID)
        findNavController().navigate(R.id.action_inventoryFragment_to_editFragment, bundle)

    }
}