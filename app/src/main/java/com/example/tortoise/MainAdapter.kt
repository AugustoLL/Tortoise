package com.example.tortoise

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tortoise.apis.skyscanner.Flight
import kotlinx.android.synthetic.main.flight_row.view.*

class MainAdapter(var rows: Int = 0): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return rows
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.flight_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = Flight.flights[position]
        holder.bind(item)
    }
}

class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(flight: Flight) = with(itemView) {
        departureDateText.text = flight.departureDate
        destinationText.text = flight.destination
        originText.text = flight.origin
        priceText.text = flight.price.toString()
        carrierText.text = flight.carrierName
    }
}