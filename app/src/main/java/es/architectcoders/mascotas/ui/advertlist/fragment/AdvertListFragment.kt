package es.architectcoders.mascotas.ui.advertlist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.model.AdvertRepository
import es.architectcoders.mascotas.ui.advertlist.viewmodel.AdvertListViewModel
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.withViewModel
import kotlinx.android.synthetic.main.advertlist_fragment.*

class AdvertListFragment : Fragment() {

    companion object {
        fun newInstance() = AdvertListFragment()
    }

    private lateinit var viewModel: AdvertListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.advertlist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = withViewModel({ AdvertListViewModel(AdvertRepository()) }) {
            observe(model, ::updateUI)
        }
    }

    private fun updateUI(model: AdvertListViewModel.UiModel) {
        when (model) {
            is AdvertListViewModel.UiModel.Content -> {
                setupAdvertList(model)
            }
            is AdvertListViewModel.UiModel.Navigation -> Unit
            is AdvertListViewModel.UiModel.Loading -> {
                progress.visibility = if (model.show) View.VISIBLE else View.GONE
            }
            is AdvertListViewModel.UiModel.Error -> {
                Snackbar.make(container, model.errorString, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupAdvertList(model: AdvertListViewModel.UiModel.Content) {
        model.adverts?.apply {
            this.forEach {
                Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
