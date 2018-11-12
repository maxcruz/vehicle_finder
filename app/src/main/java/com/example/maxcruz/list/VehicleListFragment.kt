package com.example.maxcruz.list


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maxcruz.VehicleFinderApplication
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
        setupList()
    }

    private fun setupList() {
        val adapter = VehicleAdapter {

        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }


}
