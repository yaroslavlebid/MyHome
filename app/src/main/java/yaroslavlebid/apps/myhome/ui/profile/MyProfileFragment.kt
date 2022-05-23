package yaroslavlebid.apps.myhome.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    private val myProfileViewModel: MyProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyProfileBinding.bind(view)

        initListeners(binding)
        initObservers()
    }

    private fun initListeners(binding: FragmentMyProfileBinding) {
        binding.run {
            logOutIcon.setOnClickListener { onLogoutClicked() }
            logout.setOnClickListener { onLogoutClicked() }
            logoutArrow.setOnClickListener { onLogoutClicked() }
        }
    }

    private fun initObservers() {
        myProfileViewModel.isLogoutSuccess.observe(viewLifecycleOwner) {
            if (it) {
                val action = MyProfileFragmentDirections.actionProfileToLoginActivity()
                findNavController().navigate(action)
            }
        }
    }

    private fun onHelpClicked() {

    }

    private fun onMyOrdersClicked() {

    }

    private fun onAddApartmentCLicked() {

    }

    private fun onHistoryClicked() {

    }

    private fun onFavoriteClicked() {

    }

    private fun onLanguageClicked() {

    }

    private fun onThemeClicked() {

    }

    private fun onLogoutClicked() {
        myProfileViewModel.doLogout()
    }
}