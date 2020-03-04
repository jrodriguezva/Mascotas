package es.architectcoders.mascotas.ui.advertlist

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.model.MyFirebaseAdvert
import es.architectcoders.mascotas.ui.common.basicDiffUtil
import es.architectcoders.mascotas.ui.common.inflate
import es.architectcoders.mascotas.ui.common.loadUrl
import kotlinx.android.synthetic.main.view_advert.view.*
import java.text.NumberFormat
import java.util.Locale

class AdvertsAdapter(
    private val listener: (MyFirebaseAdvert) -> Unit,
    private val favListener: (MyFirebaseAdvert) -> Unit
) :
    RecyclerView.Adapter<AdvertsAdapter.ViewHolder>() {

    var adverts: List<MyFirebaseAdvert> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_advert, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = adverts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val advert = adverts[position]
        holder.bind(advert)
        holder.itemView.setOnClickListener { listener(advert) }
        holder.itemView.advertFav.setOnClickListener { favListener(advert) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(advert: MyFirebaseAdvert) {
            itemView.advertTitle.text = advert.title
            if (advert.photoUrl.isEmpty()) {
                itemView.advertCover.setImageResource(R.drawable.ic_launcher_foreground)
            } else {
                itemView.advertCover.loadUrl(advert.photoUrl)
            }
            itemView.advertPrize.text = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(advert.price)
            itemView.advertRecent.visibility = if (advert.recent) View.VISIBLE else View.GONE
        }
    }
}

@BindingAdapter("advert_items")
fun RecyclerView.setAdvertItems(users: List<MyFirebaseAdvert>?) {
    (adapter as? AdvertsAdapter)?.let {
        it.adverts = users ?: emptyList()
    }
}