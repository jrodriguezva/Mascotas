package es.architectcoders.mascotas.ui.login.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.model.LoginRepository
import es.architectcoders.mascotas.ui.common.observe
import es.architectcoders.mascotas.ui.common.withViewModel
import es.architectcoders.mascotas.ui.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = withViewModel({ LoginViewModel(LoginRepository(FirebaseAuth.getInstance())) }) {
            observe(model, ::updateUI)
        }
        login.setOnClickListener {
            viewModel.signIn(usernameEdit.text.toString(), passwordEdit.text.toString())
        }
        register.setOnClickListener {
            viewModel.createAccount(usernameEdit.text.toString(), passwordEdit.text.toString())
        }
    }

    private fun updateUI(model: LoginViewModel.UiModel) {
        when (model) {
            is LoginViewModel.UiModel.Content -> {
                setupUser(model)
            }
            is LoginViewModel.UiModel.ValidateForm -> {
                when (model.field) {
                    is LoginViewModel.Field.Email -> usernameLayout.error = model.field.error
                    is LoginViewModel.Field.Password -> passwordLayout.error = model.field.error
                }
            }
            is LoginViewModel.UiModel.Navigation -> Unit
            is LoginViewModel.UiModel.Loading -> {
                progress.visibility = if (model.show) View.VISIBLE else View.GONE
            }
            is LoginViewModel.UiModel.Error -> {
                Snackbar.make(container, model.errorString, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupUser(model: LoginViewModel.UiModel.Content) {
        model.user.apply {
            if (this != null) {
                usernameEdit.setText(email)
                passwordEdit.setText("")
            } else {
                usernameEdit.setText("")
                passwordEdit.setText("")
            }
        }
    }
}