package yaroslavlebid.apps.myhome.ui.login.sign_up

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentSignInBinding
import yaroslavlebid.apps.myhome.databinding.FragmentSignUpBinding
import yaroslavlebid.apps.myhome.utils.TextInputValidator

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
                    email.editText?.text.toString(),
                    password.editText?.text.toString()
                )
            }

            goToSignIn.setOnClickListener {
                Timber.d("Go to sign in...")
                findNavController().popBackStack()
            }
        }
    }

    private fun initObservers(binding: FragmentSignUpBinding) {
        signUpViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingIndicator.visibility = View.VISIBLE
                binding.signUp.text = ""
            }
            else {
                binding.loadingIndicator.visibility = View.INVISIBLE
                binding.signUp.text = getString(R.string.create_account)
            }
        }

        signUpViewModel.registrationStatus.observe(viewLifecycleOwner) { registrationResult ->
            if (registrationResult.status == RegistrationStatus.SUCCESS) {
                val action = SignUpFragmentDirections.actionSignUpFragmentToEditProfileFragment(isItFirstSetup = true)
                findNavController().navigate(action)
            } else {
                val errorMessage = when (registrationResult.status) {
                    RegistrationStatus.CUSTOM_ERROR -> getString(R.string.something_went_wrong)
                    else -> registrationResult.status?.let {
                        RegistrationStatusMap.getErrorMessage(it)
                    }
                }
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}