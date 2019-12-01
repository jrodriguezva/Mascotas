package es.architectcoders.mascotas.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import es.architectcoders.mascotas.ui.fragments.ProfileFavoriteFragment
import es.architectcoders.mascotas.ui.fragments.ProfileOnSaleFragment
import es.architectcoders.mascotas.ui.fragments.ProfileReviewFragment


class ProfilePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ProfileOnSaleFragment()
            }
            1 -> ProfileFavoriteFragment()
            else -> {
                return ProfileReviewFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }


    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "En venta"
            1 -> "Favoritos"
            else -> {
                return "Reviews"
            }
        }
    }
}