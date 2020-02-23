package es.architectcoders.mascotas.ui.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.model.AdvertRepository
import es.architectcoders.mascotas.ui.advertlist.AdvertsAdapter
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.withViewModel
import es.architectcoders.mascotas.ui.profile.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.advertlist_fragment.*
import kotlinx.android.synthetic.main.profile_favorite_fragment.*


class ProfileFavoriteFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter : AdvertsAdapter

    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_favorite_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mLayoutManager = LinearLayoutManager(context)
        rvFavorite.layoutManager = mLayoutManager

        viewModel = withViewModel({ ProfileViewModel(AdvertRepository()) }) {
            observe(model, ::updateUI)
        }
        adapter = AdvertsAdapter(viewModel::onAdvertClicked, viewModel::onAdvertFavClicked)
        rvFavorite.adapter = adapter
        rvFavorite.layoutManager = GridLayoutManager(context, 2)
        rvFavorite.adapter?.notifyDataSetChanged()
    }
    private fun updateUI(model: ProfileViewModel.UiModel) {
        progressFavorite.visibility =
            if (model is ProfileViewModel.UiModel.Loading) {
                View.VISIBLE
            }
            else {
                View.GONE
            }

        when (model) {
            is ProfileViewModel.UiModel.Content -> {
                adapter.adverts = model.adverts
            }
            is ProfileViewModel.UiModel.Navigation -> TODO("DetailActivity pending")
            is ProfileViewModel.UiModel.Error -> {
                Snackbar.make(container, model.errorString, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
