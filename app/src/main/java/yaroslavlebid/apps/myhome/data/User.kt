package yaroslavlebid.apps.myhome.data

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val photoUrl: String = "",
    val registrationTimestamp: Long = 0L
)
