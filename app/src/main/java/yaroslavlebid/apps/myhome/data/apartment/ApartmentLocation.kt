package yaroslavlebid.apps.myhome.data.apartment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApartmentLocation(
    val country: String = "",
    val city: String = "",
    val street: String = "",
    val numberOfHouse: String = "",
    val coordsX: Double = 0.0,
    val coordsY: Double = 0.0,
) : Parcelable
