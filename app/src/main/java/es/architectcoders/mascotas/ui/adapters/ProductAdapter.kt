package es.architectcoders.mascotas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.data.models.Product
import java.util.ArrayList

class ProductAdapter(internal var context: Context, private val products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = products[position]
        holder.name.text = product.name
        holder.prize.text = product.prize
        Glide.with(context).load(product.imageURL).apply(RequestOptions().circleCrop()).into(holder.imageProduct)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun getItem(position: Int): Product {
        return products[position]
    }

    class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageProduct: ImageView = view.findViewById(R.id.imageProduct)
        var name: TextView = view.findViewById(R.id.nameProduct)
        var prize: TextView = view.findViewById(R.id.prizeProduct)
    }
}
