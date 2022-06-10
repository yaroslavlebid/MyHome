package yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.ParseException
import timber.log.Timber
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.data.review.Review
import yaroslavlebid.apps.myhome.repository.ApartmentRepository
import yaroslavlebid.apps.myhome.repository.ReviewRepository
import yaroslavlebid.apps.myhome.utils.toApartment
import yaroslavlebid.apps.myhome.utils.toReview

class ApartmentViewModel(
    private val apartmentRepository: ApartmentRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _apartment = MutableLiveData<Apartment>()
    val apartment: LiveData<Apartment> = _apartment

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    fun loadReviews(apartmentId: String) {
        reviewRepository.getReviewsForApartment(apartmentId).addOnSuccessListener { query ->
            if (query.isEmpty) _reviews.value = emptyList()
            else {
                val reviewList = mutableListOf<Review>()
                query.forEach {
                    try {
                        reviewList.add(it.toReview())
                    } catch (e: ParseException) {
                        Timber.e("FATAL Can't parse data")
                    }
                }
                _reviews.value = reviewList
            }

        }.addOnFailureListener {
            Timber.e(it, "Can't load reviews")
        }
    }

    fun loadApartment(apartmentId: String) {
        apartmentRepository.getApartmentById(apartmentId).addOnSuccessListener {
            try {
                _apartment.value = it.toApartment()
            } catch (e: ParseException) {
                Timber.e("FATAL Can't parse data")
            }
        }.addOnFailureListener {
            Timber.e(it, "Can't load apartment")
        }
    }
}