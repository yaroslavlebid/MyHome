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
import com.bumptech.glide.Glide
import yaroslavlebid.apps.myhome.utils.setDrawableImage


class ProfileSetupFragment : Fragment(R.layout.fragment_profile_setup) {

    private val profileViewModel: ProfileViewModel by viewModel()

    private lateinit var binding: FragmentProfileSetupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileSetupBinding.bind(view)

        initListenters(binding)
        initObservers(binding)
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
                profileViewModel.setUpProfile(
                    firstName.text.toString(),
                    lastName.text.toString(),
                    phoneNumber.text.toString(),
                    profileImage.toString(),
                    dateOfBirth.toString()
                )
            }
        }
    }

    private fun initObservers(binding: FragmentProfileSetupBinding) {
        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) binding.loadingIndicator.visibility = View.VISIBLE
            else binding.loadingIndicator.visibility = View.GONE
        }

        profileViewModel.canConfirmProfile.observe(viewLifecycleOwner) {
            if (it) HomeActivity.start(requireActivity())
        }
    }

    private fun pickProfileImage() {
        Album.image(this)
            .singleChoice()
            .camera(true)
            .onResult {
                binding.profileImage.setLocalImage(Uri.fromFile(File(it.first().path)), true)
            }
            .onCancel {
                Timber.d("Canceled select image")
            }
            .start()
    }
}