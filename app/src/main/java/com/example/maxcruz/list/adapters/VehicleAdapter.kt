package com.example.maxcruz.list.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.maxcruz.domain.models.Point
import com.example.maxcruz.vehiclefinder.R
import com.example.maxcruz.vehiclefinder.databinding.ItemVehicleBinding

/**
 * List item vehicle adapter
 */
class VehicleAdapter(private val onItemClick: (point: Point) -> Unit) :
    RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    private lateinit var list: MutableList<Point>

    /**
     * Replace all the elements on the list and fill with the new one received as parameter
     */
    fun updateList(newList: List<Point>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemVehicleBinding>(inflater, R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class VehicleViewHolder(private val binding: ItemVehicleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(point: Point) {
            binding.item = point
            binding.root.setOnClickListener { onItemClick(point) }
            binding.executePendingBindings()
        }

    }

}