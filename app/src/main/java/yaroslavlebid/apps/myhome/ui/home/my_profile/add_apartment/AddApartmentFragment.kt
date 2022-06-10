package yaroslavlebid.apps.myhome.ui.home.my_profile.add_apartment

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import java.util.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import timber.log.Timber
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.data.apartment.ApartmentAdvantage
import yaroslavlebid.apps.myhome.data.apartment.ApartmentLocation
import yaroslavlebid.apps.myhome.data.apartment.ApartmentType
import yaroslavlebid.apps.myhome.data.apartment.Photo
import yaroslavlebid.apps.myhome.data.session.Session
import yaroslavlebid.apps.myhome.databinding.FragmentAddApartmentBinding
import yaroslavlebid.apps.myhome.utils.getText
import kotlin.collections.ArrayList

class AddApartmentFragment : Fragment(R.layout.fragment_add_apartment) {

    private var photosUrl = mutableListOf<String>()
    private lateinit var albumWidget: Widget

    private val addAppartmentViewModel: AddApartmentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddApartmentBinding.bind(view)
        when (getResources().getConfiguration().uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> albumWidget =
                getKoin().get<Widget>(named("dark_widget"))
            Configuration.UI_MODE_NIGHT_NO -> albumWidget =
                getKoin().get<Widget>(named("light_widget"))
        }

        initViews(binding)
        initListeners(binding)
        initObservers(binding)
    }

    private fun initViews(binding: FragmentAddApartmentBinding) {
        binding.run {
            photosUrl.add("")
            val adapter = ApartmentPhotosAdapter(photosUrl).apply {
                isEmpty = true
                onLongPhotoClick = {

                }
                pickImages = {
                    pickImages {
                        addAppartmentViewModel.uploadImages(it.map { it.path })
                    }
                }
            }
            photoRecycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
            photoRecycler.adapter = adapter

        }
    }

    private fun initListeners(binding: FragmentAddApartmentBinding) {
        binding.run {
            continueButton.setOnClickListener {
                val advantages = mutableListOf<ApartmentAdvantage>()
                advantagesChips.forEach {
                    if ((it as Chip).isChecked)
                        advantages.add(ApartmentAdvantage(title = it.text.toString()))
                }
                val apartment = getApartmentFromView(this).copy(
                    id = UUID.randomUUID().toString(),
                    photos = photosUrl.map { Photo(mediumImageUrl = it) },
                    advantages = advantages,
                    coverPhotoId = 0,
                    publicationTimestamp = Date().time
                )
                addAppartmentViewModel.addApartmentToList(apartment)
            }
        }
    }

    private fun initObservers(binding: FragmentAddApartmentBinding) {
        addAppartmentViewModel.isImagesLoading.observe(viewLifecycleOwner) {
            binding.continueButton.isEnabled = !it
            val adapter = if (it)
                ApartmentPhotosAdapter(photosUrl).apply {
                    isEmpty = false
                    isLoading = true
                }
            else {
                ApartmentPhotosAdapter(photosUrl).apply {
                    isEmpty = false
                    isLoading = false
                }
            }
            binding.photoRecycler.adapter = adapter
        }

        addAppartmentViewModel.linksToImages.observe(viewLifecycleOwner) {
            photosUrl.clear()
            photosUrl.addAll(it)
            binding.photoRecycler.adapter?.notifyDataSetChanged()
        }

        addAppartmentViewModel.apartmentLiveData.observe(viewLifecycleOwner) {
            val action =
                AddApartmentFragmentDirections.actionAddApartmentFragmentToAddedApartmentsFragment()
            findNavController().navigate(action)
        }
    }

    private fun pickImages(onResult: (ArrayList<AlbumFile>) -> Unit) {
        Album.image(this)
            .multipleChoice()
            .selectCount(10)
            .widget(albumWidget)
            .camera(false)
            .onResult {
                onResult.invoke(it)
            }
            .onCancel {
                Timber.d("Canceled select image")
            }
            .start()
    }

    private fun getApartmentFromView(binding: FragmentAddApartmentBinding): Apartment {
        binding.run {
            val title = title.getText()
            val description = subtitle.getText()
            val location = location.getText()
            val iban = iban.getText()
            val apartmentType: ApartmentType = when {
                flatRadio.isChecked -> ApartmentType.FLAT
                hotelRadio.isChecked -> ApartmentType.HOTEL
                privateHouseRadio.isChecked -> ApartmentType.PRIVATE_HOUSE
                else -> ApartmentType.PRIVATE_HOUSE
            }
            return Apartment(
                title = title,
                description = description,
                location = ApartmentLocation(city = location),
                typeOfApartment = apartmentType
            )
        }
    }
}