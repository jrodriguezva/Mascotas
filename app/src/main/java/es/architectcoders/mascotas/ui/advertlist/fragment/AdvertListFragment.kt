package es.architectcoders.mascotas.ui.advertlist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.model.AdvertRepository
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
    private lateinit var adapter : AdvertsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.advertlist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = withViewModel({ AdvertListViewModel(AdvertRepository()) }) {
            observe(model, ::updateUI)
        }
        adapter = AdvertsAdapter(viewModel::onAdvertClicked, viewModel::onAdvertFavClicked)
        recycler.adapter = adapter
    }

    private fun updateUI(model: AdvertListViewModel.UiModel) {
        progress.visibility =
            if (model is AdvertListViewModel.UiModel.Loading) {
                View.VISIBLE
            }
            else {
                View.GONE
            }

        when (model) {
            is AdvertListViewModel.UiModel.Content -> {
                adapter.adverts = model.adverts
            }
            is AdvertListViewModel.UiModel.Navigation -> TODO("DetailActivity pending")
            is AdvertListViewModel.UiModel.Error -> {
                Snackbar.make(container, model.errorString, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
