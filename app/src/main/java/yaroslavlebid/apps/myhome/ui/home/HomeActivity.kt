package yaroslavlebid.apps.myhome.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import yaroslavlebid.apps.myhome.databinding.ActivityHomeBinding
import yaroslavlebid.apps.myhome.ui.apartments.ApartmentListFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // show toolbar
        // show apartmentList fragment

        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, ApartmentListFragment())
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