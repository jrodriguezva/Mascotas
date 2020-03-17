package es.architectcoders.mascotas.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import es.architectcoders.mascotas.databinding.LoginFragmentBinding
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advert.AdvertListActivity
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.startActivity
import es.architectcoders.mascotas.ui.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by lifecycleScope.viewModel(this)
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LoginFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            observe(nav, ::navigate)
            observe(error, ::showError)
        }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun navigate(event: Event<Int>) {
        event.getContentIfNotHandled()?.apply {
            activity?.startActivity<AdvertListActivity> {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }.also { activity?.finish() }
        }
    }

    private fun showError(event: Event<String>) {
        event.getContentIfNotHandled()?.apply {
            Snackbar.make(container, this, Snackbar.LENGTH_SHORT).show()
        }
    }
}