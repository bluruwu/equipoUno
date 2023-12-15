package com.univalle.widgetinventory.view.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.univalle.widgetinventory.R
import com.univalle.widgetinventory.databinding.FragmentEditBinding
import com.univalle.widgetinventory.databinding.FragmentInventoryBinding
import com.univalle.widgetinventory.viewmodel.InventoryViewModel

class InventoryHomeFragment : Fragment() {
    private lateinit var binding: FragmentInventoryBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val inventoryViewModel : InventoryViewModel by viewModels()

       override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
           binding = FragmentInventoryBinding.inflate(inflater)
           binding.lifecycleOwner = this
           return binding.root
    }


}