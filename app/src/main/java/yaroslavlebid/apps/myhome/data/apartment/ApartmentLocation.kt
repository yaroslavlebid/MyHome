package yaroslavlebid.apps.myhome.data.apartment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApartmentLocation(
    val country: String = "",
    val city: String = "",
    val street: String = "",
    val numberOfHouse: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
) : Parcelable
