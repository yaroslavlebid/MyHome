package yaroslavlebid.apps.myhome.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import yaroslavlebid.apps.myhome.databinding.ActivityLoginBinding
import yaroslavlebid.apps.myhome.ui.home.HomeActivity
import yaroslavlebid.apps.myhome.ui.profile.ProfileActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // val user = FirebaseAuth.getInstance().currentUser
       // if (user != null) {
       //     Timber.d ("User loggined, start HomeActivity")
           //HomeActivity.start(this)
        ProfileActivity.start(this)
        //}
        //SearchTestActivity.start(this)

        /*Timber.d("User isn't loged in, show SignInFragment")
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, SignInFragment())
            .commit()*/
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(activity, intent, null)
        }
    }

}