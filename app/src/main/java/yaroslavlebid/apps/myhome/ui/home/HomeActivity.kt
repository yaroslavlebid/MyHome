package yaroslavlebid.apps.myhome.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.CallLog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.data.apartment.ApartmentLocation
import yaroslavlebid.apps.myhome.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    var locations = arrayOf<ApartmentLocation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locations = emptyArray()

        val navController = Navigation.findNavController(this, R.id.fragment_container)
        //NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
        navController.addOnDestinationChangedListener { it, destination, _ ->
            if (destination.id != R.id.search
                && destination.id != R.id.map && destination.id != R.id.favorite
                && destination.id != R.id.profile && it.currentDestination?.id == R.id.search
                && it.currentDestination?.id == R.id.map && it.currentDestination?.id == R.id.favorite
                && it.currentDestination?.id == R.id.profile
            ) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.map) {
                val bundle = Bundle().apply {
                    putParcelableArray("markers", locations)
                }
                navController.navigate(item.itemId, bundle)
            } else navController.navigate(item.itemId)
            true
        }

        binding.bottomNavigation.selectedItemId = R.id.search
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(activity, intent, null)
        }
    }
}