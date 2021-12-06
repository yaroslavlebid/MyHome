package yaroslavlebid.apps.myhome.ui.login.ui.login.sign_up

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentSignUpBinding
import yaroslavlebid.apps.myhome.ui.home.HomeActivity

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    val signUpViewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSignUpBinding.bind(view)

        initListeners(binding)
        initObservers(binding)
    }

    private fun initListeners(binding: FragmentSignUpBinding) {
        binding.run {
            signUp.setOnClickListener {
                signUpViewModel.registerUser(
                    email.text.toString(),
                    password.text.toString()
                )
            }
        }
    }

    private fun initObservers(binding: FragmentSignUpBinding) {
        signUpViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) binding.loadingIndicator.visibility = View.VISIBLE
            else binding.loadingIndicator.visibility = View.GONE
        }

        signUpViewModel.registrationStatus.observe(viewLifecycleOwner) { registrationResult ->
            if (registrationResult.status == RegistrationStatus.SUCCESS) {
                activity?.let { HomeActivity.start(it) }
            } else {
                activity?.let {
                    Toast.makeText(it, registrationResult.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}