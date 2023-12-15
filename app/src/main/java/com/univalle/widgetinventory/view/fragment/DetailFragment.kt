package com.univalle.widgetinventory.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.widgetinventory.R
import com.univalle.widgetinventory.databinding.FragmentDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "ProductID"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val db =FirebaseFirestore.getInstance()
    private var productID=6969


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productID= arguments?.getInt("ProductID")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()

    }

    private fun setUpView(){
        val connection=db.collection("producto").document(productID.toString()).get()
        connection.addOnSuccessListener { documents->
            if(documents.get("nombre")!=null){
                val priceTemp=documents.get("precio")
                val stockTemp=documents.get("cantidad")
                val name=documents.get("nombre")
                val totalTemp=priceTemp.toString().toFloat() * stockTemp.toString().toFloat()
                binding.productNameDetail.text="$name"
                binding.priceDetail.text="$priceTemp"
                binding.stockDetail.text="$stockTemp"
                binding.totalPriceDetail.text="$totalTemp"
            }else{
                findNavController().navigate(R.id.action_detailFragment_to_inventoryFragment)
            }


        }

        binding.contentToolbar.toolbar.setNavigationOnClickListener{
            findNavController().navigate(R.id.action_detailFragment_to_inventoryFragment)
        }
        binding.deleteProductButton.setOnClickListener {
            //invocar lo de eliminar
            db.collection("producto").document(productID.toString()).delete().addOnSuccessListener {
                findNavController().navigate(R.id.action_detailFragment_to_inventoryFragment)
            }
        }
        binding.editProductButton.setOnClickListener {
            val bundle=Bundle()
            bundle.putInt("ProductID",productID)
            findNavController().navigate(R.id.action_detailFragment_to_editFragment, bundle)

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment DetailFragment.
         */
        @JvmStatic
        fun newInstance(param1:Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}