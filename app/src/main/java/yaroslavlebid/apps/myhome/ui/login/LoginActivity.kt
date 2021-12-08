package yaroslavlebid.apps.myhome.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber
import yaroslavlebid.apps.myhome.databinding.ActivityLoginBinding
import yaroslavlebid.apps.myhome.repository.UserRepository
import yaroslavlebid.apps.myhome.ui.home.HomeActivity
import yaroslavlebid.apps.myhome.ui.login.sign_in.SignInFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Timber.d ("User loggined, start HomeActivity")
            HomeActivity.start(this)
        }

        Timber.d("User isn't loged in, show SignInFragment")
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, SignInFragment())
            .commit()
    }

}