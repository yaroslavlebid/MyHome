package yaroslavlebid.apps.myhome.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.ActivityHomeBinding
import yaroslavlebid.apps.myhome.ui.login.LoginActivity
import yaroslavlebid.apps.myhome.ui.login.sign_in.SignInFragment
import yaroslavlebid.apps.myhome.ui.profile.ProfileSetupFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // show toolbar
        // show apartmentList fragment

        binding.textview.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            LoginActivity.start(this)
        }
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, ProfileSetupFragment())
            .commit()
    }

    override fun onBackPressed() {
        // do nothing
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(activity, intent, null)
        }
    }
}