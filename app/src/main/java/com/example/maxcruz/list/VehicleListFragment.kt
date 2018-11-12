package com.example.maxcruz.list


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maxcruz.VehicleFinderApplication
import com.example.maxcruz.domain.models.Point
import com.example.maxcruz.list.adapters.VehicleAdapter
import com.example.maxcruz.vehiclefinder.R
import com.example.maxcruz.vehiclefinder.databinding.FragmentListBinding
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


/**
 * Display a list of vehicles to select
 */
class VehicleListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as VehicleFinderApplication).getListComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.vm = ViewModelProviders.of(this, viewModelFactory)[VehicleListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelCommands()
        setupList()
    }

    private fun observeViewModelCommands() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[VehicleListViewModel::class.java]
        viewModel.command.observe(this, Observer {
            when (it) {
                is VehicleListViewModel.Command.Error -> showError(it.message)
            }
        })
    }

    private fun setupList() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[VehicleListViewModel::class.java]
        val adapter = VehicleAdapter(viewModel.getAddress) {
            navigateToMap(it)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        viewModel.loadPoints()
    }

    private fun showError(message: String) {
        activity?.let {
            Snackbar.make(layoutContainer, message, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun navigateToMap(point: Point) {
        println(point)
    }

}
