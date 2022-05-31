package yaroslavlebid.apps.myhome.ui.edit_profile

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.api.widget.Widget
import java.io.File
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import timber.log.Timber
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentEditProfileBinding
import yaroslavlebid.apps.myhome.utils.getText
import yaroslavlebid.apps.myhome.utils.setImageFromUrl
import yaroslavlebid.apps.myhome.utils.setLocalImage


class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private val profileViewModel: ProfileViewModel by viewModel()

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var albumWidget: Widget

    private val editProfileFragmentArgs: EditProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        when (getResources().getConfiguration().uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> albumWidget =
                getKoin().get<Widget>(named("dark_widget"))
            Configuration.UI_MODE_NIGHT_NO -> albumWidget =
                getKoin().get<Widget>(named("light_widget"))
        }
        if (!editProfileFragmentArgs.isItFirstSetup) {
            requireActivity().getOnBackPressedDispatcher().addCallback(this) {
                findNavController().popBackStack()
            }
        }
        if (!editProfileFragmentArgs.isItFirstSetup) {
            requireActivity().getOnBackPressedDispatcher().addCallback(this) {
                findNavController().popBackStack()
            }
        }

        initViews(binding)
        initListenters(binding)
        initObservers(binding)

        profileViewModel.initUser()
    }

    private fun initViews(binding: FragmentEditProfileBinding) {
        binding.confirmButton.isEnabled = false
        if (editProfileFragmentArgs.isItFirstSetup) {
            binding.profileHeader.titleText.text = getString(R.string.set_up_your_profile_text)
            binding.profileHeader.toolbar.navigationIcon = null
        } else {
            binding.profileHeader.titleText.text = getString(R.string.edit_profile)
            binding.profileHeader.toolbar.setNavigationIcon(R.drawable.arrow_back)
            binding.profileHeader.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initListenters(binding: FragmentEditProfileBinding) {
        binding.run {
            profileHeader.profileImage.setOnClickListener {
                pickProfileImage()
            }

            changePicture.setOnClickListener {
                pickProfileImage()
            }

            firstName.editText?.doOnTextChanged { _, _, _, _ ->
                requestEnableConfirmButton(this)
            }
            lastName.editText?.doOnTextChanged { _, _, _, _ ->
                requestEnableConfirmButton(this)
            }
            phoneNumber.editText?.doOnTextChanged { _, _, _, _ ->
                requestEnableConfirmButton(this)
            }
            privacyCheckBox.setOnCheckedChangeListener { _, _ ->
                requestEnableConfirmButton(this)
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
            } else {
                binding.progressBarConfirm.visibility = View.GONE
                binding.confirmButton.text = getString(R.string.confirm_button_text)
            }
        }

        profileViewModel.isProfileConfirmed.observe(viewLifecycleOwner) {
            if (it) {
                if (editProfileFragmentArgs.isItFirstSetup) {
                    val action =
                        EditProfileFragmentDirections.actionEditProfileFragmentToHomeActivity()
                    findNavController().navigate(action)
                } else {
                    findNavController().popBackStack()
                }
            }
        }

        profileViewModel.isProfileImageLoaded.observe(viewLifecycleOwner) { result ->
            if (result) binding.profileHeader.profileImage.setLocalImage(
                Uri.fromFile(
                    File(
                        tempImagePath
                    )
                ), true
            )
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
                    profileHeader.profileImage.setImageFromUrl(user.photoUrl, true)
                    firstName.editText?.setText(user.firstName)
                    lastName.editText?.setText(user.lastName)
                    phoneNumber.editText?.setText(user.phoneNumber)
                }
            } else {
                binding.profileHeader.titleText.text = getString(R.string.set_up_your_profile_text)
            }
        }

        profileViewModel.canConfirmProfile.observe(viewLifecycleOwner) {
            binding.confirmButton.isEnabled = it
        }
    }

    private fun requestEnableConfirmButton(binding: FragmentEditProfileBinding) {
        binding.run {
            profileViewModel.requestCanConfirmProfile(
                firstName.getText(),
                lastName.getText(),
                phoneNumber.getText(),
                privacyCheckBox.isChecked
            )
        }
    }

    private fun pickProfileImage() {
        Album.image(this)
            .singleChoice()
            .widget(albumWidget)
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