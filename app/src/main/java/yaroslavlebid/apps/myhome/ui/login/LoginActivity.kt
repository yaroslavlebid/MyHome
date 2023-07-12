package yaroslavlebid.apps.myhome.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import yaroslavlebid.apps.myhome.databinding.ActivityLoginBinding
import yaroslavlebid.apps.myhome.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Timber.d("User loggined, start HomeActivity")
            HomeActivity.start(this)
        }

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}