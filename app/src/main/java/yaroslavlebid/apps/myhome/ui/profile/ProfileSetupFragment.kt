package yaroslavlebid.apps.myhome.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentProfileSetupBinding
import yaroslavlebid.apps.myhome.ui.home.HomeActivity
import yaroslavlebid.apps.myhome.utils.MediaLoader
import yaroslavlebid.apps.myhome.utils.setLocalImage
import java.io.File
import com.yanzhenjie.album.AlbumFile

import androidx.annotation.NonNull
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import yaroslavlebid.apps.myhome.utils.setDrawableImage
import yaroslavlebid.apps.myhome.utils.setImageFromUrl


class ProfileSetupFragment : Fragment(R.layout.fragment_profile_setup) {

    private val profileViewModel: ProfileViewModel by viewModel()

    private lateinit var binding: FragmentProfileSetupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileSetupBinding.bind(view)

        initListenters(binding)
        initObservers(binding)

        profileViewModel.initUser()
    }

    private fun initListenters(binding: FragmentProfileSetupBinding) {
        binding.run {
            profileImage.setOnClickListener {
                pickProfileImage()
            }

            fabAddPhoto.setOnClickListener {
                pickProfileImage()
            }

            confirmButton.setOnClickListener {
                profileViewModel.updateUser(
                    firstName = firstName.text.toString(),
                    lastName = lastName.text.toString(),
                    phoneNumber = phoneNumber.text.toString(),
                    dateOfBirth = dateOfBirth.text.toString()
                )
                profileViewModel.confirmProfile()
            }
        }
    }

    var temporaryPath = ""

    private fun initObservers(binding: FragmentProfileSetupBinding) {
        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) binding.loadingIndicator.visibility = View.VISIBLE
            else binding.loadingIndicator.visibility = View.GONE
        }

        profileViewModel.canConfirmProfile.observe(viewLifecycleOwner) {
            if (it) HomeActivity.start(requireActivity())
        }

        profileViewModel.isProfileImageLoaded.observe(viewLifecycleOwner) { result ->
            if (result) binding.profileImage.setLocalImage(Uri.fromFile(File(temporaryPath)), true)
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
                    profileImage.setImageFromUrl(user.photoUrl, true)
                    firstName.setText(user.firstName)
                    lastName.setText(user.lastName)
                    // todo: set other fields
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
                    temporaryPath = it.path
                }
            }
            .onCancel {
                Timber.d("Canceled select image")
            }
            .start()
    }
}