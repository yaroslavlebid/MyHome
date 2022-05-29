package yaroslavlebid.apps.myhome.data

import com.google.firebase.firestore.DocumentId
import java.util.*

data class User(
    val id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var email: String = "",
    var photoUrl: String = "",
    val registrationTimestamp: Long = 0L
) {
    fun isProfileOnlyWithEmail() =
        if (firstName.isEmpty() && lastName.isEmpty() && phoneNumber.isEmpty() && photoUrl.isEmpty()) {
            true
        } else {
            false
        }
}
