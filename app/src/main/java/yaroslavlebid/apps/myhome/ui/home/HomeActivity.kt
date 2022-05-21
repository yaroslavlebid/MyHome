package yaroslavlebid.apps.myhome.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.ActivityHomeBinding
import yaroslavlebid.apps.myhome.ui.apartments.ApartmentListFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.fragment_container)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }

    override fun onBackPressed() {
        // do nothing
    }
}