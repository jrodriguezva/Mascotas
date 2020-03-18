package es.architectcoders.mascotas.ui.advert.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import es.architectcoders.mascotas.databinding.AdvertlistFragmentBinding
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advert.AdvertsAdapter
import es.architectcoders.mascotas.ui.advert.NewAdvertActivity
import es.architectcoders.mascotas.ui.advert.viewmodel.AdvertListViewModel
import es.architectcoders.mascotas.ui.advert.viewmodel.event.AdvertNavigationEvent
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.startActivity
import kotlinx.android.synthetic.main.advertlist_fragment.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class AdvertListFragment : Fragment() {

    companion object {
        fun newInstance() = AdvertListFragment()
    }

    private val viewModel: AdvertListViewModel by lifecycleScope.viewModel(this)

    private lateinit var binding: AdvertlistFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AdvertlistFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            observe(nav, ::navigate)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.recycler.adapter = AdvertsAdapter(viewModel::onAdvertClicked, viewModel::onAdvertFavClicked)
    }

    private fun navigate(event: Event<AdvertNavigationEvent>) {
        event.getContentIfNotHandled()?.apply {
            when (this) {
                AdvertNavigationEvent.CreateAdvertNavigation -> activity?.startActivity<NewAdvertActivity> {}
                is AdvertNavigationEvent.AdvertDetailNavigation -> Snackbar.make(
                    container,
                    "Pending: navigate to detail of product ${this.id}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}
