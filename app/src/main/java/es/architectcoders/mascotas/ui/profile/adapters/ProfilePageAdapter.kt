package es.architectcoders.mascotas.ui.profile.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import es.architectcoders.mascotas.ui.profile.fragments.ProfileFavoriteFragment
import es.architectcoders.mascotas.ui.profile.fragments.ProfileOnSaleFragment
import es.architectcoders.mascotas.ui.profile.fragments.ProfileReviewFragment


class ProfilePageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            PROFILE_ON_SALE_FRAGMENT -> {
                ProfileOnSaleFragment()
            }
            PROFILE_FAVORITE_FRAGMENT -> ProfileFavoriteFragment()
            else -> {
                return ProfileReviewFragment()
            }
        }
    }

    override fun getCount(): Int {
        return TOTAL_PAGE
    }


    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            PROFILE_ON_SALE_FRAGMENT -> "En venta"
            PROFILE_FAVORITE_FRAGMENT -> "Favoritos"
            else -> {
                return "Reviews"
            }
        }
    }

    companion object {
        const val PROFILE_ON_SALE_FRAGMENT = 0
        const val PROFILE_FAVORITE_FRAGMENT = 1
        const val TOTAL_PAGE = 3
    }
}