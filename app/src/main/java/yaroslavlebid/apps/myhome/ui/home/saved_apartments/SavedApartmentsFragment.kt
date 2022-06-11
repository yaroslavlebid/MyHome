package yaroslavlebid.apps.myhome.ui.home.saved_apartments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.databinding.FragmentSavedApartmentsBinding
import yaroslavlebid.apps.myhome.ui.home.apartments.adapter.ApartmentListAdapter

class SavedApartmentsFragment : Fragment(R.layout.fragment_saved_apartments) {

    private val savedApartmentsViewModel: SavedApartmentsViewModel by viewModel()
    val apartmentList: MutableList<Apartment> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSavedApartmentsBinding.bind(view)

        initViews(binding)
        initObservers(binding)

        savedApartmentsViewModel.requestSavedApartments()
    }

    private fun initViews(binding: FragmentSavedApartmentsBinding) {
        binding.run {
            val adapter = ApartmentListAdapter(apartmentList).apply {
                onItemClickListener = {
                    val action =
                        SavedApartmentsFragmentDirections.actionFavoriteToApartmentFragment(it, "0", "0")
                    findNavController().navigate(action)
                }
                bookmarkClickListener = {
                    savedApartmentsViewModel.removeApartmentFromFavorites(it.id)
                }
                onMapClickListener = {
                    val action = SavedApartmentsFragmentDirections.actionFavoriteToMap(arrayOf(it.location))
                    findNavController().navigate(action)
                }
                isSavedInFavorite = true
            }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter

            swipeRefresh.setOnRefreshListener {
                savedApartmentsViewModel.requestSavedApartments()
            }
        }
    }

    private fun initObservers(binding: FragmentSavedApartmentsBinding) {
        savedApartmentsViewModel.apartments.observe(viewLifecycleOwner) {
            apartmentList.clear()
            apartmentList.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

        savedApartmentsViewModel.removedApartment.observe(viewLifecycleOwner) {
            try {
                val removed = apartmentList.first { apartment -> apartment.id == it }
                val index = apartmentList.indexOf(removed)
                apartmentList.remove(removed)
                binding.recyclerView.adapter?.notifyItemRemoved(index)
            } catch (e: NoSuchElementException) {
                Timber.e(e, "Something went wrong")
            }
        }

        savedApartmentsViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }
    }
}