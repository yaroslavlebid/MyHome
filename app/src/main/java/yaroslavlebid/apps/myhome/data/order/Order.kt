package yaroslavlebid.apps.myhome.data.order

import com.google.firebase.firestore.DocumentId
import yaroslavlebid.apps.myhome.data.User
import yaroslavlebid.apps.myhome.data.apartment.ApartmentAdvantage
import yaroslavlebid.apps.myhome.data.apartment.ApartmentLocation
import yaroslavlebid.apps.myhome.data.apartment.ApartmentType
import yaroslavlebid.apps.myhome.data.apartment.Photo
import yaroslavlebid.apps.myhome.data.apartment.Price

data class Order(
    @DocumentId
    val id: String = "",
    val customerId:String = "",
    val clientId: String = "",
    val apartmentId: String = "",
    val roomId: String = "",
    val sumOfMoney: Price = Price(),
    val transactionId: String = "",
    val timestamp: Long = 0L,
    val isCanceled: Boolean = false
)
