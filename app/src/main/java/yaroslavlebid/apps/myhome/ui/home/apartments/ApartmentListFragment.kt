package yaroslavlebid.apps.myhome.ui.home.apartments

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.lapism.search.widget.MaterialSearchView
import com.lapism.search.widget.NavigationIconCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.databinding.FragmentApartmentListBinding
import yaroslavlebid.apps.myhome.ui.home.HomeActivity
import yaroslavlebid.apps.myhome.ui.home.apartments.adapter.ApartmentListAdapter
import yaroslavlebid.apps.myhome.utils.toSelectedDate

class ApartmentListFragment : Fragment(R.layout.fragment_apartment_list) {

    private val apartmentViewModel: ApartmentListViewModel by viewModel()
    val apartmentList: MutableList<Apartment> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentApartmentListBinding.bind(view)

        initViews(binding)
        initListeners(binding)
        initObservers(binding)

        apartmentViewModel.requestApartments()
    }

    private fun initViews(binding: FragmentApartmentListBinding) {
        binding.run {
            val adapter = ApartmentListAdapter(apartmentList).apply {
                onItemClickListener = {
                    val action = ApartmentListFragmentDirections.actionSearchToApartmentFragment(
                        it,
                        searchHelper.guestsButton.text.toString(),
                        searchHelper.calendarButton.text.toString()
                    )
                    findNavController().navigate(action)
                }
                bookmarkClickListener = {
                    apartmentViewModel.addApartmentToFavorite(it)
                }
                onMapClickListener = {
                    val action = ApartmentListFragmentDirections.actionSearchToMap(arrayOf(it.location))
                    findNavController().navigate(action)
                }
            }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter

            materialSearchBar.apply {
                navigationIconCompat = NavigationIconCompat.SEARCH
                setHint(context.getString(R.string.search_hint))
            }
            materialSearchView.apply {
                navigationIconCompat = NavigationIconCompat.ARROW
                setHint(context.getString(R.string.where_are_you_going_hint))
            }

            swipeRefresh.setOnRefreshListener {
                apartmentViewModel.requestApartments()
            }
        }
    }

    private fun initListeners(binding: FragmentApartmentListBinding) {
        binding.run {
            materialSearchBar.apply {
                setOnClickListener {
                    materialSearchView.requestFocus()
                    searchHelper.root.visibility = View.VISIBLE
                    swipeRefresh.constraintsTopToBottomOf(searchHelper.root.id)
                    filterAndSortLayout.visibility = View.GONE
                }
                setNavigationOnClickListener {
                    materialSearchView.requestFocus()
                    searchHelper.root.visibility = View.VISIBLE
                    swipeRefresh.constraintsTopToBottomOf(searchHelper.root.id)
                    filterAndSortLayout.visibility = View.GONE
                }
            }

            materialSearchView.apply {
                setNavigationOnClickListener {
                    materialSearchView.clearFocus()
                    searchHelper.root.visibility = View.GONE
                    filterAndSortLayout.visibility = View.VISIBLE
                    swipeRefresh.constraintsTopToBottomOf(filterAndSortLayout.id)
                }

                setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: CharSequence) {
                    }

                    override fun onQueryTextSubmit(query: CharSequence) {

                    }
                })
                setOnFocusChangeListener(object : MaterialSearchView.OnFocusChangeListener {
                    override fun onFocusChange(hasFocus: Boolean) {

                    }
                })
            }

            searchHelper.calendarButton.setOnClickListener {
                pickDate().addOnPositiveButtonClickListener {
                    val dateRange = "${it.first.toSelectedDate()} - ${it.second.toSelectedDate()}"
                    searchHelper.calendarButton.text = dateRange
                }
            }
        }
    }

    private fun initObservers(binding: FragmentApartmentListBinding) {
        apartmentViewModel.apartments.observe(viewLifecycleOwner) { resultList ->
            (requireActivity() as HomeActivity).locations = resultList.map { it.location }.toTypedArray()
            apartmentList.clear()
            apartmentList.addAll(resultList)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

        apartmentViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }

    }

    private fun View.constraintsTopToBottomOf(viewId: Int) {
        val params = this.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom = viewId
        this.requestLayout()
    }

    private fun pickDate(): MaterialDatePicker<androidx.core.util.Pair<Long, Long>> {

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now()).build()

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    androidx.core.util.Pair(
                        MaterialDatePicker.todayInUtcMilliseconds(),
                        (MaterialDatePicker.todayInUtcMilliseconds() + 86_400_000)
                    )
                )
                .setCalendarConstraints(constraintsBuilder)
                .build()

        dateRangePicker.show(childFragmentManager, "date_range")

        return dateRangePicker
    }

}