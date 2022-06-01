package yaroslavlebid.apps.myhome.data.apartment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApartmentAdvantage(
    val title: String = "",
    val description: String = "",
    val iconResId: Int = 0
) : Parcelable
