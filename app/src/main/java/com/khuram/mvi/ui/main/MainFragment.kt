package com.khuram.mvi.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.khuram.mvi.R
import com.khuram.mvi.databinding.FragmentMainBinding
import com.khuram.mvi.model.Breed
import com.khuram.mvi.ui.DataStateListener
import com.khuram.mvi.ui.detail.DetailFragment
import com.khuram.mvi.ui.main.state.MainStateEvent
import com.khuram.mvi.util.TopSpacingItemDecoration
import java.lang.ClassCastException


class MainFragment: Fragment(), BreedListAdapter.Listeners {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var dataStateListener: DataStateListener

    private var blogListAdapter: BreedListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        subscribeObservers()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            blogListAdapter = BreedListAdapter(this@MainFragment)
            adapter = blogListAdapter
        }
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            dataState?.let { dataStateListener.onDataStateChange(dataState) }

            dataState?.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.breeds?.let {
                        viewModel.setBreedListData(it)
                    }
                }
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            viewState?.breeds?.let {
                blogListAdapter?.submitList(it)
            }
        }
    }

    override fun onItemSelected(position: Int, item: Breed) {
        viewModel.selectedItem = item
        activity?.run {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, DetailFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        blogListAdapter = null
        _binding = null
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) { }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.action_refresh -> viewModel.setStateEvent(MainStateEvent.GetBreedsEvent)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}