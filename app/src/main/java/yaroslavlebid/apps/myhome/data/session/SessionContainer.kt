package yaroslavlebid.apps.myhome.data.session

import com.google.firebase.auth.FirebaseAuth

object Session {
    val userId: String
    get() = FirebaseAuth.getInstance().currentUser?.uid ?: throw(Exception("User not authorized yet"))
}