package yaroslavlebid.apps.myhome.utils

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import timber.log.Timber
import yaroslavlebid.apps.myhome.data.User
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.exceptions.DocumentParseException

fun DocumentSnapshot.toUser() = this.toObject(User::class.java)

fun DocumentSnapshot.toApartment() = this.toObject(Apartment::class.java) ?: throw DocumentParseException("$this")

fun handleParseError(errorLiveData: MutableLiveData<Exception>, block: () -> Unit) {
    try {
        block.invoke()
    } catch (e: Exception) {
        errorLiveData.value = e
        Timber.e(e, "Parse data error!")
    }
}