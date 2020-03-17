package es.architectcoders.mascotas.ui.advert.fragment

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
import es.architectcoders.mascotas.databinding.NewAdvertFragmentBinding
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advert.viewmodel.NewAdvertViewModel
import es.architectcoders.mascotas.ui.advert.viewmodel.event.NewAdvertNavigationEvent
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.toBase64
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class NewAdvertFragment : Fragment() {

    companion object {
        fun newInstance() = NewAdvertFragment()
    }

    private val viewModel: NewAdvertViewModel by lifecycleScope.viewModel(this)

    private lateinit var binding: NewAdvertFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = NewAdvertFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            observe(nav, ::navigate)
            observe(error, ::showError)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
    }

    private fun navigate(event: Event<NewAdvertNavigationEvent>) {
        event.getContentIfNotHandled()?.let {
            when (it) {
                NewAdvertNavigationEvent.PickerNavigation -> {
                    ImagePicker.with(this)
                        .crop()
                        .compress(maxSize = 1024)
                        .maxResultSize(width = 1080, height = 1080)
                        .start()
                }
                is NewAdvertNavigationEvent.CreateAdvertNavigation -> activity?.finish()
            }
        }
    }

    private fun showError(event: Event<String>) {
        event.getContentIfNotHandled()?.apply {
            Snackbar.make(container, this, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                viewModel.setImage(ImagePicker.getFile(data)?.toBase64())
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
