package yaroslavlebid.apps.myhome.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import yaroslavlebid.apps.myhome.data.User


private const val COLLECTION_USERS = "users"

interface UserRepository {
    fun addUserToDb(user: User) : Task<Void>

    fun getUserById(uid: String): Task<DocumentSnapshot>

    fun updateUser(user: User): Task<Void>
}

class UserRepositoryImpl(private val db: FirebaseFirestore) : UserRepository{
    override fun addUserToDb(user: User) = db.collection(COLLECTION_USERS).document(user.id).set(user)

    override fun getUserById(uid: String) = db.collection(COLLECTION_USERS).document(uid).get()

    override fun updateUser(user: User) = db.collection(COLLECTION_USERS).document(user.id).set(user)
}