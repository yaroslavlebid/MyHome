package yaroslavlebid.apps.myhome.data.apartment

import com.google.firebase.firestore.DocumentId
import yaroslavlebid.apps.myhome.data.User
import yaroslavlebid.apps.myhome.data.review.Review

data class Apartment(
    @DocumentId
    val id: String = "",

    val title: String = "",
    val desription: String = "",
    val publicationTimestamp: Long = 0,
    val pricePerDayInDollars: Float = 0f,
    val photos: List<ApartmentPhoto> = listOf(),
    val location: ApartmentLocation = ApartmentLocation(),
    val author: User = User(),
    val typeOfApartment: ApartmentType = ApartmentType.PRIVATE_HOUSE,
    val advantages: List<ApartmentAdvantage> = listOf(),
    val reviews: List<Review> = listOf()
)
