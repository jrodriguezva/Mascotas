package es.architectcoders.mascotas.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.data.models.Product
import es.architectcoders.mascotas.ui.adapters.ProductAdaper
import kotlinx.android.synthetic.main.profile_on_sale_fragment.*
import androidx.recyclerview.widget.GridLayoutManager


class ProfileOnSaleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_on_sale_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mLayoutManager = LinearLayoutManager(context)
        rvOnSale.layoutManager = mLayoutManager
        rvOnSale.itemAnimator = DefaultItemAnimator()

        val list: ArrayList<Product> = ArrayList()
        list.add(Product("Collar perro", "12€", "http://i.imgur.com/DvpvklR.png"))
        list.add(Product("Collar perro 2", "8€", "http://i.imgur.com/DvpvklR.png"))
        list.add(Product("Collar perro 3", "8€", "http://i.imgur.com/DvpvklR.png"))
        list.add(Product("Collar perro 4", "8€", "http://i.imgur.com/DvpvklR.png"))
        list.add(Product("Collar perro 5", "8€", "http://i.imgur.com/DvpvklR.png"))

        rvOnSale!!.adapter = ProductAdaper(context!!, list)
        rvOnSale.layoutManager = GridLayoutManager(context, 2)
        rvOnSale.adapter!!.notifyDataSetChanged()
    }

}
