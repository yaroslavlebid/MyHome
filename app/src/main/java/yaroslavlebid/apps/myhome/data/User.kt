package yaroslavlebid.apps.myhome.data

import com.google.firebase.firestore.DocumentId
import java.util.*

data class User(
    @DocumentId
    val id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    val email: String = "",
    var photoUrl: String = "",
    var dateOfBirth: String = "",
    val registrationTimestamp: Long = 0L
) {
    fun isProfileOnlyWithEmail() =
        if (firstName.isEmpty() && lastName.isEmpty() && phoneNumber.isEmpty() && photoUrl.isEmpty()
            && dateOfBirth.isEmpty()
        ) {
            true
        } else {
            false
        }
}
