package es.architectcoders.mascotas.ui.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import es.architectcoders.mascotas.databinding.ProfileFragmentBinding
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advert.AdvertDetailActivity
import es.architectcoders.mascotas.ui.advert.AdvertsAdapter
import es.architectcoders.mascotas.ui.advert.viewmodel.event.AdvertNavigationEvent
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.startActivity
import es.architectcoders.mascotas.ui.profile.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.profile_fragment.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    enum class LIST_TIPES { ON_SALE, FAVORITES}
    private val viewModel: ProfileViewModel by lifecycleScope.viewModel(this)
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ProfileFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.apply {
            observe(nav, ::navigate)
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.recyclerProfile.adapter = AdvertsAdapter(viewModel::onAdvertClicked, viewModel::onAdvertFavClicked)

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.position == 0) viewModel.refresh(LIST_TIPES.ON_SALE)
                else viewModel.refresh(LIST_TIPES.FAVORITES)
            }
        })
    }


    private fun navigate(event: Event<AdvertNavigationEvent>) {
        event.getContentIfNotHandled()?.apply {
            activity?.startActivity<AdvertDetailActivity> { putExtra(AdvertDetailActivity.ADVERT_ID, id) }
        }
    }
}
