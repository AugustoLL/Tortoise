package com.example.tortoise.apis.skyscanner

import android.util.Log

class Flight (val id: Int, val origin: String, val destination: String, val carrierName: String, val price: Int, var isDirect: Boolean = true, var departureDate: String = "2020-12-01"){

    companion object ListOfFlights{
        val flights = mutableListOf<Flight>()
        private const val TAG = "FLIGHT"

        fun setFlightsList(){
            var origin: String
            var destination: String
            var carrierName: String

            for (quotes in ApiRequest.skyScanner.quotes) {

                origin = getPlaceNameFromId(quotes.outboundLeg.originId)
                destination = getPlaceNameFromId(quotes.outboundLeg.destinationId)
                carrierName = (getCarrierNameFromId(quotes.outboundLeg.carrierIds[0]))

                val f = addFlight(quotes.quoteId, origin, destination, carrierName, quotes.minPrice, quotes.direct, quotes.outboundLeg.departureDate)
                Log.i(TAG,"CREATED FLIGHT ${f.id}, with Origin: ${f.origin} and Destination: ${f.destination}, with carrier: ${f.carrierName}, for a total of ${f.price} and a Departure Date of ${f.departureDate}")
            }
        }

        private fun addFlight(id: Int, origin: String, destination: String, carrierName: String, price: Int = 0, isDirect: Boolean = true, departureDate: String = "2020-12-01"): Flight {
            //Log.i(TAG,"Adding flight $id (Origin: $origin & Destination: $destination) to flights")
            val flight = Flight(id, origin, destination, carrierName, price, isDirect, departureDate)
            flights.add(flight)
            return flight
        }

        private fun getPlaceNameFromId(id: Int): String{
            var placeName = ""
            for (place in ApiRequest.skyScanner.places){

                if (placeName.isNotBlank())
                    break

                when (place.placeId) {
                    id -> {
                        placeName = place.name
                        //Log.i(TAG,"Found Match for OriginId: ${id}, with name: $placeName")
                    }
                }
            }
            return placeName
        }

        private fun getCarrierNameFromId(id: Int): String{
            var carrierName : String = ""

            for (carrier in ApiRequest.skyScanner.carriers){
                when (carrier.carrierId){
                    id -> {
                        carrierName = carrier.name
                    }
                }
            }
            return carrierName
        }
    }
}