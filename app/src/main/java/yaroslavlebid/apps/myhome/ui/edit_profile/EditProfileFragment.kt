package yaroslavlebid.apps.myhome.ui.edit_profile

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yanzhenjie.album.Album
import java.io.File
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentEditProfileBinding
import yaroslavlebid.apps.myhome.utils.setImageFromUrl
import yaroslavlebid.apps.myhome.utils.setLocalImage


class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private val profileViewModel: ProfileViewModel by viewModel()

    private lateinit var binding: FragmentEditProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)

        initListenters(binding)
        initObservers(binding)

        profileViewModel.initUser()
    }

    private fun initListenters(binding: FragmentEditProfileBinding) {
        binding.run {
            profileHeader.profileImage.setOnClickListener {
                pickProfileImage()
            }

            changePicture.setOnClickListener {
                pickProfileImage()
            }

            confirmButton.setOnClickListener {
                profileViewModel.updateUser(
                    firstName = firstName.editText?.text.toString(),
                    lastName = lastName.editText?.text.toString(),
                    phoneNumber = phoneNumber.editText?.text.toString(),
                )
                profileViewModel.confirmProfile()
            }
        }
    }

    var tempImagePath = ""

    private fun initObservers(binding: FragmentEditProfileBinding) {
        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarConfirm.visibility = View.VISIBLE
                binding.confirmButton.text = ""
            }
            else {
                binding.progressBarConfirm.visibility = View.GONE
                binding.confirmButton.text = getString(R.string.confirm_button_text)
            }
        }

        profileViewModel.canConfirmProfile.observe(viewLifecycleOwner) {
            if (it) {
                val action = EditProfileFragmentDirections.actionEditProfileFragmentToHomeActivity()
                findNavController().navigate(action)
            }
        }

        profileViewModel.isProfileImageLoaded.observe(viewLifecycleOwner) { result ->
            if (result) binding.profileHeader.profileImage.setLocalImage(Uri.fromFile(File(tempImagePath)), true)
            else {
                Toast.makeText(
                    requireContext(),
                    "Can't upload image, try later",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        profileViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            if (!user.isProfileOnlyWithEmail()) {
                binding.run {
                    profileHeader.titleText.text = getString(R.string.edit_profile)
                    profileHeader.profileImage.setImageFromUrl(user.photoUrl, true)
                    firstName.editText?.setText(user.firstName)
                    lastName.editText?.setText(user.lastName)
                    phoneNumber.editText?.setText(user.phoneNumber)
                }
            }
        }
    }

   private fun pickProfileImage() {
        Album.image(this)
            .singleChoice()
            .camera(true)
            .onResult {
                it.firstOrNull()?.let {
                    profileViewModel.loadProfileImage(it.path)
                    tempImagePath = it.path
                }
            }
            .onCancel {
                Timber.d("Canceled select image")
            }
            .start()
    }
}