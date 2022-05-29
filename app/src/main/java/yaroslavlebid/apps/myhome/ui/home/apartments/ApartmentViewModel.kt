package yaroslavlebid.apps.myhome.ui.home.apartments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.repository.ApartmentRepository
import yaroslavlebid.apps.myhome.ui.helpers.Event

class ApartmentListViewModel(private val apartmentRepository: ApartmentRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _apartments = MutableLiveData<List<Apartment>>()
    val apartments: LiveData<List<Apartment>> = _apartments

    private val _requestStatus = MutableLiveData<Event<RequestApartmentsStatus>>()
    val requestStatus: LiveData<Event<RequestApartmentsStatus>> = _requestStatus

    fun requestApartments() {
        _isLoading.value = true
        apartmentRepository.getApartmentList()
            .addOnSuccessListener { snapshot ->
                val apartmentList = mutableListOf<Apartment>()
                for (document in snapshot) {
                    try {
                        val apartment = document.toObject(Apartment::class.java)
                        apartmentList.add(apartment)
                    } catch (error: Throwable) {
                        Timber.e("Can't parse document to object!", error)
                    }
                }

                _apartments.value = apartmentList
                _requestStatus.value = Event(RequestApartmentsStatus.SUCCESS)
            }
            .addOnFailureListener {
                // todo: it will not work, if user don't have internet connection
                Timber.e("Error, when trying to retreive data from remote db", it)
            }
            .addOnCompleteListener {
                _isLoading.value = false
            }
    }

    // fixme: for test
    fun addMockApartmentsToDb() {
        apartmentRepository.addMockApartmentsToDb()
    }
}