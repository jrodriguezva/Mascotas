package es.architectcoders.mascotas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.data.models.Product
import java.util.ArrayList

class ProductAdaper(internal var context: Context, products: List<Product>) :
    RecyclerView.Adapter<ProductAdaper.ProductHolder>() {

    private val productList: List<Product>

    init {
        this.productList = ArrayList(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = productList[position]
        holder.name.text = product.name
        holder.prize.text = product.prize
        Picasso.get().load(product.imageURL).into(holder.imageProduct)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun getItem(position: Int): Product {
        return productList[position]
    }

    class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageProduct: ImageView = view.findViewById(R.id.imageProduct)
        var name: TextView = view.findViewById(R.id.nameProduct)
        var prize: TextView = view.findViewById(R.id.prizeProduct)
    }
}
