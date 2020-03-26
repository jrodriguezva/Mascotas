package es.architectcoders.mascotas.ui.profile.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import es.architectcoders.mascotas.databinding.EditProfileFragmentBinding
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.toBase64
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
            observe(nav, ::navigate)
            observe(error, ::showError)
        }
        binding.viewmodel = editProfileViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }


    private fun showError(event: Event<String>) {
        event.getContentIfNotHandled()?.apply {
            Snackbar.make(container, this, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun navigate(event: Event<ProfileNavigationEvent>) {
        event.getContentIfNotHandled()?.let {
            when (it) {
                ProfileNavigationEvent.PickerNavigation -> {
                    ImagePicker.with(this)
                        .crop()
                        .compress(maxSize = 1024)
                        .maxResultSize(width = 1080, height = 1080)
                        .start()
                }
                else -> activity?.finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                editProfileViewModel.setImage(ImagePicker.getFile(data)?.toBase64())
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}