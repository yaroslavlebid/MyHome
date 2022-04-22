package yaroslavlebid.apps.myhome.ui.login.sign_in

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentSignInBinding
import yaroslavlebid.apps.myhome.ui.home.HomeActivity
import yaroslavlebid.apps.myhome.ui.login.sign_up.RegistrationStatus
import yaroslavlebid.apps.myhome.ui.login.sign_up.RegistrationStatusMap
import yaroslavlebid.apps.myhome.ui.login.sign_up.SignUpFragment
import kotlin.math.sign

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val signInViewModel: SignInViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSignInBinding.bind(view)

        initListeners(binding)
        initObservers(binding)
    }

    private fun initListeners(binding: FragmentSignInBinding) {
        binding.signIn.setOnClickListener {
            signInViewModel.signIn(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
        }
        binding.register.setOnClickListener {
            SignUpFragment.show(parentFragmentManager)
        }
    }

    private fun initObservers(binding: FragmentSignInBinding) {
        binding.run {
            signInViewModel.isLoading.observe(viewLifecycleOwner) {
                if (it) binding.loadingIndicator.visibility = View.VISIBLE
                else binding.loadingIndicator.visibility = View.GONE
            }

            signInViewModel.loginStatus.observe(viewLifecycleOwner) { event ->
                if (event.status == LoginStatus.SUCCESS) {
                    HomeActivity.start(requireActivity())
                } else {
                    val errorMessage = when(event.status) {
                        LoginStatus.CUSTOM_ERROR -> event.customMessage
                        else -> event.status?.let {
                            LoginStatusMap.getErrorMessage(it)
                        }
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}