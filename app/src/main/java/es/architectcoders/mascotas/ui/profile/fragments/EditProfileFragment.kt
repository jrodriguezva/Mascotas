package es.architectcoders.mascotas.ui.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import es.architectcoders.mascotas.databinding.EditProfileFragmentBinding
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.profile.viewmodel.EditProfileViewModel
import es.architectcoders.mascotas.ui.profile.viewmodel.event.ProfileNavigationEvent
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class EditProfileFragment: Fragment(){

    companion object {
        fun newInstance() = EditProfileFragment()
    }

    private val editProfileViewModel: EditProfileViewModel by lifecycleScope.viewModel(this)
    private lateinit var binding: EditProfileFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = EditProfileFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editProfileViewModel.apply {
            observe(nav, ::finishActivity)
            observe(error, ::showError)
        }
        binding.viewmodel = editProfileViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun finishActivity(event: Event<ProfileNavigationEvent>) {
        event.getContentIfNotHandled()?.apply { activity?.finish() }
    }

    private fun showError(event: Event<String>) {
        event.getContentIfNotHandled()?.apply {
            Snackbar.make(container, this, Snackbar.LENGTH_SHORT).show()
        }
    }
}