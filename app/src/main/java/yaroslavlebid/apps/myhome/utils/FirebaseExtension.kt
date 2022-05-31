package yaroslavlebid.apps.myhome.utils

import com.google.firebase.firestore.DocumentSnapshot
import yaroslavlebid.apps.myhome.data.User

fun DocumentSnapshot.toUser() = this.toObject(User::class.java)