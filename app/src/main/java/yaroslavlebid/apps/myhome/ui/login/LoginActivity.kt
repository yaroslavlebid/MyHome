package yaroslavlebid.apps.myhome.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import yaroslavlebid.apps.myhome.databinding.ActivityLoginBinding
import yaroslavlebid.apps.myhome.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Timber.d ("User loggined, start HomeActivity")
            HomeActivity.start(this)
        }

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}