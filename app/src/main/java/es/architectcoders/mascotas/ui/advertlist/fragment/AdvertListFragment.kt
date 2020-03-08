package es.architectcoders.mascotas.ui.advertlist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.architectcoders.data.repository.AdvertRepository
import com.google.android.material.snackbar.Snackbar
import es.architectcoders.mascotas.databinding.AdvertlistFragmentBinding
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advertlist.AdvertsAdapter
import es.architectcoders.mascotas.ui.advertlist.viewmodel.AdvertListViewModel
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.withViewModel
import kotlinx.android.synthetic.main.advertlist_fragment.*

class AdvertListFragment : Fragment() {

    companion object {
        fun newInstance() = AdvertListFragment()
    }

    private lateinit var viewModel: AdvertListViewModel
    private lateinit var binding: AdvertlistFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AdvertlistFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = withViewModel({ AdvertListViewModel(AdvertRepository()) }) {
            observe(nav, ::navigate)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.recycler.adapter = AdvertsAdapter(viewModel::onAdvertClicked, viewModel::onAdvertFavClicked)
    }

    private fun navigate(event: Event<Long>) {
        event.getContentIfNotHandled()?.apply {
            Snackbar.make(container, "Pending: navigate to detail of product $this", Snackbar.LENGTH_SHORT).show()
//            activity?.startActivity<AdvertDetail> {
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//            }.also { activity?.finish() }
        }
    }
}
