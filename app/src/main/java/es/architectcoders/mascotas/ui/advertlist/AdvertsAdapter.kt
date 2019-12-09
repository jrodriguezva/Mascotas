package es.architectcoders.mascotas.ui.advertlist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.model.MyFirebaseAdvert
import es.architectcoders.mascotas.ui.common.basicDiffUtil
import es.architectcoders.mascotas.ui.common.inflate
import es.architectcoders.mascotas.ui.common.loadUrl
import kotlinx.android.synthetic.main.view_advert.view.*
import java.util.*

class AdvertsAdapter(private val listener: (MyFirebaseAdvert) -> Unit) :
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
        val movie = adverts[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(advert: MyFirebaseAdvert) {
            itemView.advertTitle.text = advert.title
            itemView.advertCover.loadUrl(advert.photoUrl)
            itemView.advertPrize.text = String.format(Locale.getDefault(),
                "%.2d", advert.price)
        }
    }

}
