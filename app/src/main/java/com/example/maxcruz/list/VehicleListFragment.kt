package com.example.maxcruz.list


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.maxcruz.VehicleFinderApplication
import com.example.maxcruz.core.BaseFragment
import com.example.maxcruz.core.toJson
import com.example.maxcruz.domain.models.Point
import com.example.maxcruz.list.adapters.VehicleAdapter
import com.example.maxcruz.vehiclefinder.R
import com.example.maxcruz.vehiclefinder.databinding.FragmentListBinding
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


/**
 * Display a list of vehicles to select
 */
class VehicleListFragment : BaseFragment() {

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

    override fun getTitle(): String {
        return getString(R.string.list_title)
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
        viewModel.getVehiclePoints().observe(this, Observer { vehicles ->
            vehicles?.let {
                adapter.updateList(it)
            }
        })
    }

    private fun navigateToMap(point: Point) {
        val bundle = Bundle()
        bundle.putString("point", point.toJson())
        NavHostFragment.findNavController(this).navigate(R.id.action_listFragment_to_mapFragment, bundle)
    }

}
