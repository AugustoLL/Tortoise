package com.example.tortoise.apis.skyscanner

import com.google.gson.annotations.SerializedName

data class InboundLeg (

	@SerializedName("CarrierIds") val carrierIds : List<Int>,
	@SerializedName("OriginId") val originId : Int,
	@SerializedName("DestinationId") val destinationId : Int,
	@SerializedName("DepartureDate") val departureDate : String
)