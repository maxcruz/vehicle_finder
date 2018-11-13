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
import com.example.maxcruz.domain.models.FleetType
import com.example.maxcruz.domain.models.Point
import com.example.maxcruz.vehiclefinder.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

/**
 * Show the selected point centered on the map
 */
class VehicleMapFragment : BaseFragment(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var googleMap: GoogleMap? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as VehicleFinderApplication).getListComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
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

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        observeViewModelCommands()
        setupParameter()
    }

    override fun getTitle(): String {
        return getString(R.string.map_title)
    }

    private fun observeViewModelCommands() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[VehicleMapViewModel::class.java]
        viewModel.command.observe(this, Observer {
            when (it) {
                is VehicleMapViewModel.Command.EmptyParameter -> navigateBack()
                is VehicleMapViewModel.Command.LoadPoint -> loadMarker(it.latitude, it.longitude, it.fleetType)
                is VehicleMapViewModel.Command.Error -> showError(it.message)
            }
        })
        viewModel.pointList.observe(this, Observer { points ->
            googleMap?.let { map ->
                points?.forEach { (coordinate, fleetType) ->
                    coordinate?.let { (latitude, longitude) ->
                        if (latitude != null && longitude != null && fleetType != null) {
                            map.addMarker(
                                MarkerOptions()
                                    .position(LatLng(latitude, longitude))
                                    .icon(BitmapDescriptorFactory.defaultMarker(getColor(fleetType)))
                                    .alpha(0.3f)
                            )
                        }
                    }
                }
            }
        })
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
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

    private fun loadMarker(latitude: Double, longitude: Double, fleetType: FleetType) {
        val color = getColor(fleetType)
        googleMap?.let { map ->
            with(map) {
                val marker = LatLng(latitude, longitude)
                moveCamera(CameraUpdateFactory.newLatLngZoom(marker, ZOOM_LEVEL))
                addMarker(
                    MarkerOptions()
                        .position(marker)
                        .icon(BitmapDescriptorFactory.defaultMarker(color))
                )

                val bounds = projection?.visibleRegion?.latLngBounds
                bounds?.let {
                    fetchNeighbors(
                        it.northeast.latitude,
                        it.northeast.longitude,
                        it.southwest.latitude,
                        it.southwest.longitude
                    )
                }
            }
        }
    }

    private fun getColor(fleetType: FleetType) = when (fleetType) {
        is FleetType.Taxi -> BitmapDescriptorFactory.HUE_ORANGE
        is FleetType.Pooling -> BitmapDescriptorFactory.HUE_AZURE
    }

    private fun fetchNeighbors(latitude1: Double, longitude1: Double, latitude2: Double, longitude2: Double) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[VehicleMapViewModel::class.java]
        viewModel.fetchNeighbors(latitude1, longitude1, latitude2, longitude2)
    }

    companion object {
        const val ZOOM_LEVEL = 13f
    }

}
