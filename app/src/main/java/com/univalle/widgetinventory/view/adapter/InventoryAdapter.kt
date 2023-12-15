import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.univalle.widgetinventory.databinding.ProductBinding
import com.univalle.widgetinventory.model.Producto
import com.univalle.widgetinventory.view.viewholder.ProductViewHolder

class InventoryAdapter(
    private val productList: MutableList<Producto>,
    private val onItemClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.setItemProduct(product)
        holder.itemView.setOnClickListener { onItemClick(product) }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
