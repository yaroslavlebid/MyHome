package yaroslavlebid.apps.myhome.data.apartment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApartmentAdvantage(
    val title: String = "",
) : Parcelable
