package com.univalle.widgetinventory.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.univalle.widgetinventory.R
import com.univalle.widgetinventory.databinding.FragmentInventoryBinding
import com.univalle.widgetinventory.view.adapter.InventoryAdapter
import com.univalle.widgetinventory.viewmodel.InventoryViewModel

class InventoryHomeFragment : Fragment() {

    private lateinit var binding: FragmentInventoryBinding
    private val inventoryViewModel : InventoryViewModel by viewModels()
    private var inventoryAdapter: InventoryAdapter? = null

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

        observerViewModel()
        setupButton()
    }

    private fun setupButton() {
        binding.contentToolbar.toolbar.setNavigationOnClickListener { onExitPressed() }
        binding.fbagregar.setOnClickListener { findNavController().navigate(R.id.action_inventoryFragment_to_addFragment) }
    }

    private fun onExitPressed() {

    }

    private fun observerViewModel() {
        observerListProducts()
    }

    private fun observerListProducts() {
        inventoryViewModel.getListProducts()
        inventoryViewModel.listProducts.observe(viewLifecycleOwner) { listaProductos ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager

            /*inventoryAdapter  = InventoryAdapter(listaProductos, { position, descriptionChallenge ->
                showDeleteDialog(position, descriptionChallenge)},{position,descriptionChallenge-> showEditDialog(position,descriptionChallenge)
            })*/

            inventoryAdapter = InventoryAdapter(listaProductos)
            recycler.adapter = inventoryAdapter
        }
    }
}