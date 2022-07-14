package yaroslavlebid.apps.myhome.ui.home.apartments

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
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
            val adapter = ApartmentListAdapter(requireContext(), apartmentList).apply {
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
                    onSearchBackClick(binding)
                }

                setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: CharSequence) {
                    }

                    override fun onQueryTextSubmit(query: CharSequence) {
                        materialSearchBar.setHint(query)
                        //apartmentViewModel.requestApartments()
                        apartmentViewModel.filterApartments(apartmentList, query.toString())
                        onSearchBackClick(binding)
                    }
                })
                setOnFocusChangeListener(object : MaterialSearchView.OnFocusChangeListener {
                    override fun onFocusChange(hasFocus: Boolean) {

                    }
                })
            }

            sortByChip.setOnClickListener {
                createSortMenu(binding).show()
            }

            searchHelper.calendarButton.setOnClickListener {
                pickDate().addOnPositiveButtonClickListener {
                    val dateRange = "${it.first.toSelectedDate()} - ${it.second.toSelectedDate()}"
                    searchHelper.calendarButton.text = dateRange
                }
            }

            searchHelper.guestsButton.setOnClickListener {
                pickPersons(this)
            }
        }
    }

    private fun createSortMenu(binding: FragmentApartmentListBinding): PopupMenu {
        val popupMenu2 = PopupMenu(requireContext(), binding.sortByChip)
        popupMenu2.inflate(R.menu.sort_menu)
        popupMenu2.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sort_by_price -> {
                    apartmentViewModel.sortByPrice(apartmentList)
                }
                R.id.sort_by_rating -> {
                    apartmentViewModel.sortByRating(apartmentList)
                }
            }
            false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu2.setForceShowIcon(true)
        }
        return popupMenu2
    }

    private fun onSearchBackClick(binding: FragmentApartmentListBinding) {
        binding.run {
            materialSearchView.clearFocus()
            searchHelper.root.visibility = View.GONE
            filterAndSortLayout.visibility = View.VISIBLE
            swipeRefresh.constraintsTopToBottomOf(filterAndSortLayout.id)
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
                .setTitleText(getString(R.string.select_dates))
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

    private fun pickPersons(binding: FragmentApartmentListBinding) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(yaroslavlebid.apps.myhome.R.layout.select_persons_layout, null)
        dialogBuilder.setView(dialogView)

        val minusButton1: ImageView = dialogView.findViewById(yaroslavlebid.apps.myhome.R.id.minusButton)
        val plusButton1: ImageView = dialogView.findViewById(yaroslavlebid.apps.myhome.R.id.addButton)
        val minusButton2: ImageView = dialogView.findViewById(yaroslavlebid.apps.myhome.R.id.minusButton2)
        val plusButton2: ImageView = dialogView.findViewById(yaroslavlebid.apps.myhome.R.id.addButton2)

        val textViewAdults: TextView = dialogView.findViewById(yaroslavlebid.apps.myhome.R.id.adults)
        val textViewChildren: TextView = dialogView.findViewById(yaroslavlebid.apps.myhome.R.id.children)

        val saveButton: Button = dialogView.findViewById(yaroslavlebid.apps.myhome.R.id.save)

        minusButton1.setOnClickListener { updateTextMinusOne(textViewAdults) }
        minusButton2.setOnClickListener { updateTextMinusOne(textViewChildren) }

        plusButton1.setOnClickListener { updateTextPlusOne(textViewAdults) }
        plusButton2.setOnClickListener { updateTextPlusOne(textViewChildren) }

        val alertDialog: AlertDialog = dialogBuilder.create()
        alertDialog.show()

        saveButton.setOnClickListener {
            binding.searchHelper.guestsButton.text = String.format(requireContext().getString(R.string.chip_people), textViewAdults.text.toString(), textViewChildren.text.toString())
            alertDialog.dismiss()
        }
    }

    private fun updateTextPlusOne(textView: TextView) {
        val num = Integer.parseInt(textView.text.toString())
        textView.text = (num + 1).toString()
    }

    private fun updateTextMinusOne(textView: TextView) {
        val num = Integer.parseInt(textView.text.toString())
        textView.text = (num - 1).toString()
    }

}