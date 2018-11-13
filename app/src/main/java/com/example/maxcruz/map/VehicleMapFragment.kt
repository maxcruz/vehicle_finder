package com.example.maxcruz.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.maxcruz.VehicleFinderApplication
import com.example.maxcruz.core.BaseFragment
import com.example.maxcruz.core.fromJson
import com.example.maxcruz.domain.models.Point
import com.example.maxcruz.vehiclefinder.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import javax.inject.Inject

/**
 *
 */
class VehicleMapFragment : BaseFragment(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as VehicleFinderApplication).getListComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelCommands()
        setupMap()
        setupParameter()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                navigateBack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onMapReady(map: GoogleMap?) {

    }

    override fun getTitle(): String {
        return getString(R.string.map_title)
    }

    private fun observeViewModelCommands() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[VehicleMapViewModel::class.java]
        viewModel.command.observe(this, Observer {
            when (it) {
                is VehicleMapViewModel.Command.EmptyParameter -> navigateBack()
            }
        })
    }

    private fun setupMap() {
        val mapFragment: SupportMapFragment? = activity?.supportFragmentManager?.
            findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    private fun setupParameter() {
        val argument = arguments?.getString("point").fromJson(Point::class.java)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[VehicleMapViewModel::class.java]
        viewModel.point = argument
    }

    private fun navigateBack() {
        NavHostFragment.findNavController(this).navigateUp()
    }

}
