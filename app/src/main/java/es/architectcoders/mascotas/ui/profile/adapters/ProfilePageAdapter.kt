package es.architectcoders.mascotas.ui.profile.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import es.architectcoders.mascotas.ui.profile.fragments.ProfileFavoriteFragment
import es.architectcoders.mascotas.ui.profile.fragments.ProfileOnSaleFragment


class ProfilePageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            PROFILE_ON_SALE_FRAGMENT -> {
                ProfileOnSaleFragment()
            }
            else -> ProfileFavoriteFragment()
        }
    }

    override fun getCount(): Int {
        return TOTAL_PAGE
    }


    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            PROFILE_ON_SALE_FRAGMENT -> "En venta"
            else -> "Favoritos"
        }
    }

    companion object {
        const val PROFILE_ON_SALE_FRAGMENT = 0
        const val PROFILE_FAVORITE_FRAGMENT = 1
        const val TOTAL_PAGE = 2
    }
}