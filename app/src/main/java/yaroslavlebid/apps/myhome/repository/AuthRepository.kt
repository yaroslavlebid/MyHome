package yaroslavlebid.apps.myhome.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun registerUser(email: String, password: String): Task<AuthResult>

    fun getCurrentFirebaseUser() : FirebaseUser?

    fun signIn (email: String, password: String): Task<AuthResult>
}

class AuthRepositoryImpl(val auth: FirebaseAuth) : AuthRepository {

    override fun registerUser(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    override fun getCurrentFirebaseUser() : FirebaseUser? = auth.currentUser

    override fun signIn(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)
}