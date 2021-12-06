package yaroslavlebid.apps.myhome.ui.login.ui.login.sign_in

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentSignInBinding
import yaroslavlebid.apps.myhome.ui.login.ui.login.sign_up.SignUpFragment

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSignInBinding.bind(view)

        initListeners(binding)
    }

    private fun initListeners(binding: FragmentSignInBinding) {
        binding.register.setOnClickListener { showRegisterFragment() }
    }

    private fun showRegisterFragment() = parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SignUpFragment())
            .addToBackStack(null)
            .commit()
}