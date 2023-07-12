package yaroslavlebid.apps.myhome.ui.home.my_profile.add_apartment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import timber.log.Timber
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.data.apartment.Photo
import yaroslavlebid.apps.myhome.repository.ApartmentRepository
import yaroslavlebid.apps.myhome.repository.StorageRepository

class AddApartmentViewModel(private val storageRepository: StorageRepository, private val apartmentRepository: ApartmentRepository) : ViewModel() {
    private val _isImagesLoading = MutableLiveData<Boolean>()
    val isImagesLoading: LiveData<Boolean> = _isImagesLoading

    private val _linksToImages = MutableLiveData<List<String>>()
    val linksToImages: LiveData<List<String>> = _linksToImages

    private val _apartmentLiveData = MutableLiveData<Apartment>()
    val apartmentLiveData: LiveData<Apartment> = _apartmentLiveData


    fun uploadImages(imagesPath: List<String>) {
        _isImagesLoading.value = true
        val files = imagesPath.map { File(it) }
        val links = mutableListOf<String>()
        files.forEachIndexed { index, file ->
            storageRepository.uploadApartmentImage(file).addOnSuccessListener {
                Timber.d("image $index uploaded")
                it.storage.downloadUrl.addOnSuccessListener {
                    links.add(it.toString())
                    if (links.count() == files.count()) {
                        _isImagesLoading.value = false
                        _linksToImages.value = links
                    }
                }
            }
        }
    }

    fun addApartmentToList(addedApartment: Apartment) {
        apartmentRepository.addApartmentToDb(addedApartment).addOnSuccessListener {
            Timber.d("apartment added")
            _apartmentLiveData.value = addedApartment
        }
    }
}