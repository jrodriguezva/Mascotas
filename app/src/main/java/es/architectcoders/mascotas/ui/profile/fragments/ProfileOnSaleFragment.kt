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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.architectcoders.data.repository.AdvertRepository
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.datasource.FirestoreDataSourceImpl
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advert.AdvertsAdapter
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.withViewModel
import es.architectcoders.mascotas.ui.profile.viewmodel.ProfileViewModel
import es.architectcoders.usescases.FindRelevantAdverts
import kotlinx.android.synthetic.main.advertlist_fragment.*
import kotlinx.android.synthetic.main.profile_on_sale_fragment.*


class ProfileOnSaleFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter: AdvertsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_on_sale_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mLayoutManager = LinearLayoutManager(context)
        rvOnSale.layoutManager = mLayoutManager

        viewModel = withViewModel({
            ProfileViewModel(
                FindRelevantAdverts(
                    AdvertRepository(
                        FirestoreDataSourceImpl(Firebase.firestore)
                    )
                )
            )
        }) {
            observe(model, ::updateUI)
        }
        adapter = AdvertsAdapter(viewModel::onAdvertClicked, viewModel::onAdvertFavClicked)
        rvOnSale.adapter = adapter
        rvOnSale.layoutManager = GridLayoutManager(context, 2)
        rvOnSale.adapter?.notifyDataSetChanged()
    }

    private fun updateUI(model: ProfileViewModel.UiModel) {
        progressOnSale.visibility =
            if (model is ProfileViewModel.UiModel.Loading) {
                View.VISIBLE
            } else {
                View.GONE
            }

        when (model) {
            is ProfileViewModel.UiModel.Content -> {
                adapter.adverts = model.adverts
            }
            is ProfileViewModel.UiModel.Navigation -> {
                viewModel = withViewModel({
                    ProfileViewModel(
                        FindRelevantAdverts(
                            AdvertRepository(
                                FirestoreDataSourceImpl(Firebase.firestore)
                            )
                        )
                    )
                }) {
                    observe(nav, ::navigateToDetailAdvert)
                }
            }
            is ProfileViewModel.UiModel.Error -> {
                Snackbar.make(container, model.errorString, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToDetailAdvert(event: Event<String>) {
        event.getContentIfNotHandled()?.apply {
            Snackbar.make(container, "Pending: navigate to detail of product $this", Snackbar.LENGTH_SHORT).show()
        }.also { activity?.finish() }
    }
}
