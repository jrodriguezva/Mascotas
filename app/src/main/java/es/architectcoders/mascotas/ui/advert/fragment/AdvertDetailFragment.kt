package es.architectcoders.mascotas.ui.advert.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import es.architectcoders.mascotas.databinding.AdvertDetailFragmentBinding
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advert.AdvertDetailActivity.Companion.ADVERT_ID
import es.architectcoders.mascotas.ui.advert.viewmodel.AdvertDetailViewModel
import es.architectcoders.mascotas.ui.common.observe
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class AdvertDetailFragment : Fragment() {

    companion object {
        fun newInstance(advertId: String): AdvertDetailFragment {
            val fragment = AdvertDetailFragment()
            val args = Bundle()
            args.putString(ADVERT_ID, advertId)
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: AdvertDetailViewModel by lifecycleScope.viewModel(this) {
        parametersOf(arguments?.get(ADVERT_ID))
    }

    private lateinit var binding: AdvertDetailFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AdvertDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            observe(error, ::showError)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
    }
    private fun showError(event: Event<String>) {
        event.getContentIfNotHandled()?.apply {
            Snackbar.make(container, this, Snackbar.LENGTH_SHORT).show()
        }
    }
}
