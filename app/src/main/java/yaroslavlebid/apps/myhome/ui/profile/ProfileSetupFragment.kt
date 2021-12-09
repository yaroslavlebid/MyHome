package yaroslavlebid.apps.myhome.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yanzhenjie.album.Album
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentProfileSetupBinding
import yaroslavlebid.apps.myhome.ui.home.HomeActivity
import java.io.File

import yaroslavlebid.apps.myhome.utils.*


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
                    dateOfBirth = dateToString(
                        dayOfBirth.text.toString().toInt(),
                        monthOfBirth.text.toString().toInt(),
                        yearOfBirth.text.toString().toInt()
                    )
                )
                profileViewModel.confirmProfile()
            }
        }
    }

    var tempImagePath = ""

    private fun initObservers(binding: FragmentProfileSetupBinding) {
        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) binding.loadingIndicator.visibility = View.VISIBLE
            else binding.loadingIndicator.visibility = View.GONE
        }

        profileViewModel.canConfirmProfile.observe(viewLifecycleOwner) {
            if (it) HomeActivity.start(requireActivity())
        }

        profileViewModel.isProfileImageLoaded.observe(viewLifecycleOwner) { result ->
            if (result) binding.profileImage.setLocalImage(Uri.fromFile(File(tempImagePath)), true)
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
                    val date = stringToDate(user.dateOfBirth)
                    dayOfBirth.setText(date.dayOfMonth.toString())
                    monthOfBirth.setText(date.monthValue.toString())
                    yearOfBirth.setText(date.year.toString())
                    phoneNumber.setText(user.phoneNumber)
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

    companion object {
        fun show(fragmentManager: FragmentManager, @IdRes container: Int) {
           fragmentManager.beginTransaction()
                .add(container, ProfileSetupFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}