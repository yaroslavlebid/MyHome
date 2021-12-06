package yaroslavlebid.apps.myhome.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

interface AuthRepository {
    fun registerUser(email: String, password: String): Task<AuthResult>
}

class AuthRepositoryImpl(val auth: FirebaseAuth) : AuthRepository {

    override fun registerUser(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)
}