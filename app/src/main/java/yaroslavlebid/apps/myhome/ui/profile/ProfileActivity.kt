package yaroslavlebid.apps.myhome.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import yaroslavlebid.apps.myhome.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MyProfileFragment.show(supportFragmentManager, binding.fragmentContainer.id)
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(activity, intent, null)
        }
    }

    override fun onBackPressed() {
        // do nothing
    }
}