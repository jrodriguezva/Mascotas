package es.architectcoders.mascotas.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import es.architectcoders.mascotas.databinding.LoginFragmentBinding
import es.architectcoders.mascotas.model.LoginRepository
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advertlist.AdvertListActivity
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.startActivity
import es.architectcoders.mascotas.ui.common.withViewModel
import es.architectcoders.mascotas.ui.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LoginFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = withViewModel({ LoginViewModel(LoginRepository(FirebaseAuth.getInstance()), ResourceProvider(resources)) }) {
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