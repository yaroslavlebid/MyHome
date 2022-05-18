package yaroslavlebid.apps.myhome.ui.profile

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyProfileBinding.bind(view)
    }

    companion object {
        fun show(fragmentManager: FragmentManager, @IdRes container: Int) {
            fragmentManager.beginTransaction()
                .add(container, MyProfileFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}