package yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentApartmentBinding
import yaroslavlebid.apps.myhome.utils.AdvantageMapper

class ApartmentFragment : Fragment(R.layout.fragment_apartment) {

    private val apartmentFragmentArgs: ApartmentFragmentArgs by navArgs()
    private val apartmentViewModel: ApartmentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentApartmentBinding.bind(view)

        initViews(binding)
        initObservers(binding)

        apartmentViewModel.loadReviews(apartmentFragmentArgs.apartment.id)
    }

    private fun initViews(binding: FragmentApartmentBinding) {
        binding.run {
            val apartment = apartmentFragmentArgs.apartment
            val adapter = CurrentApartmentPhotosAdapter(apartment.photos.map { it.mediumImageUrl })
            photoRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
            photoRecycler.adapter = adapter
            toolbar.title = apartment.title
            description.text = apartment.description
            location.text = apartment.location.city
            apartment.advantages.forEach {
                advantagesChips.addView(createAdvantageChip(it.title))
            }
            mapButton.setOnClickListener {

            }
            shareButton.setOnClickListener {

            }
            selectedDateChip.text = apartmentFragmentArgs.selectedDates
            selectedPersonsChip.text = apartmentFragmentArgs.persons
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun createAdvantageChip(name: String): Chip {
        val chip = layoutInflater.inflate(R.layout.item_chip_advantage_layout, null, false) as Chip
        chip.text = name
        AdvantageMapper.mapAdvantageNameToResId(requireContext(), name)
            ?.let { chip.setChipIconResource(it) }
        return chip
    }

    fun initObservers(binding: FragmentApartmentBinding) {
        apartmentViewModel.reviews.observe(viewLifecycleOwner) {
            val adapter = CurrentApartmentReviewsAdapter(it).apply {
                onLikeClicked = {

                }
            }
            binding.reviewsRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
            binding.reviewsRecycler.adapter = adapter
        }
    }
}