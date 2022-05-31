package yaroslavlebid.apps.myhome.ui.home.my_profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentMyProfileBinding
import yaroslavlebid.apps.myhome.utils.setImageFromUrl

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    private val myProfileViewModel: MyProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyProfileBinding.bind(view)

        initViews(binding)
        initListeners(binding)
        initObservers(binding)

        myProfileViewModel.requestProfileImage()
    }

    private fun initViews(binding: FragmentMyProfileBinding) {
        binding.profileHeader.toolbar.visibility = View.GONE
    }

    private fun initListeners(binding: FragmentMyProfileBinding) {
        binding.run {
            editProfileButton.setOnClickListener {
                val action = MyProfileFragmentDirections.actionProfileToEditProfileFragment2(false)
                findNavController().navigate(action)
            }

            helpLayout.setOnClickListener {
                val action = MyProfileFragmentDirections.actionProfileToHelpFragment()
                findNavController().navigate(action)
            }

            myOrdersLayout.setOnClickListener {
                val action = MyProfileFragmentDirections.actionProfileToMyOrdersFragment()
                findNavController().navigate(action)
            }

            addApartmentLayout.setOnClickListener {
                val action = MyProfileFragmentDirections.actionProfileToAddApartmentFragment()
                findNavController().navigate(action)
            }

            addedApartmentLayout.setOnClickListener {
                val action = MyProfileFragmentDirections.actionProfileToAddedApartmentsFragment()
                findNavController().navigate(action)
            }

            historyLayout.setOnClickListener {
                val action = MyProfileFragmentDirections.actionProfileToHistoryFragment()
                findNavController().navigate(action)
            }

            notificationLayout.setOnClickListener {
                val action = MyProfileFragmentDirections.actionProfileToNotificationsFragment()
                findNavController().navigate(action)
            }

            languageLayout.setOnClickListener {
                val action = MyProfileFragmentDirections.actionProfileToLanguageFragment()
                findNavController().navigate(action)
            }

            themeLayout.setOnClickListener {
                val action = MyProfileFragmentDirections.actionProfileToThemeFragment()
                findNavController().navigate(action)
            }

            logoutLayout.setOnClickListener {
                myProfileViewModel.doLogout()
            }
        }
    }

    private fun initObservers(binding: FragmentMyProfileBinding) {
        myProfileViewModel.isLogoutSuccess.observe(viewLifecycleOwner) {
            if (it) {
                val action = MyProfileFragmentDirections.actionProfileToLoginActivity()
                findNavController().navigate(action)
            }
        }

        myProfileViewModel.profileImage.observe(viewLifecycleOwner) {
            binding.profileHeader.profileImage.setImageFromUrl(it, true)
        }
    }

}