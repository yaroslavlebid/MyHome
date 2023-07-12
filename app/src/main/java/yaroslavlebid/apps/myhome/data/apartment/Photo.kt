package yaroslavlebid.apps.myhome.data.apartment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val smallImageUrl: String = "",
    val mediumImageUrl: String = "",
    val largeImageUrl: String = ""
) : Parcelable
