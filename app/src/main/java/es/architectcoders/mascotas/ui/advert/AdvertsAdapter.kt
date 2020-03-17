package es.architectcoders.mascotas.ui.advert

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.architectcoders.domain.Advert
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.databinding.ViewAdvertBinding
import es.architectcoders.mascotas.ui.common.basicDiffUtil
import es.architectcoders.mascotas.ui.common.bindingInflate
import kotlinx.android.synthetic.main.view_advert.view.*

class AdvertsAdapter(
    private val listener: (Advert) -> Unit,
    private val favListener: (Advert) -> Unit
) :
    RecyclerView.Adapter<AdvertsAdapter.ViewHolder>() {

    var adverts: List<Advert> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            parent.bindingInflate(
                R.layout.view_advert,
                false
            )
        )

    override fun getItemCount(): Int = adverts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val advert = adverts[position]
        holder.advertBinding.advert = advert
        holder.itemView.setOnClickListener { listener(advert) }
        holder.itemView.advertFav.setOnClickListener { favListener(advert) }
    }

    class ViewHolder(val advertBinding: ViewAdvertBinding) : RecyclerView.ViewHolder(advertBinding.root)
}

@BindingAdapter("advert_items")
fun RecyclerView.setAdvertItems(users: List<Advert>?) {
    (adapter as? AdvertsAdapter)?.let {
        it.adverts = users ?: emptyList()
    }
}