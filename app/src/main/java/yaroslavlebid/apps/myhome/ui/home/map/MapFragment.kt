package yaroslavlebid.apps.myhome.ui.home.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentMapBinding


class MapFragment : Fragment(R.layout.fragment_map) {

    private val args: MapFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize view
        val binding = FragmentMapBinding.bind(view)

        // Initialize map fragment
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        supportMapFragment?.getMapAsync { googleMap ->
            val locations = args.markers.map { LatLng(it.longitude, it.latitude) }
            locations.forEach {
                googleMap.addMarker(MarkerOptions().position(it))
            }
            if (!locations.isEmpty()) googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations[0], 15f))
        }
    }
}