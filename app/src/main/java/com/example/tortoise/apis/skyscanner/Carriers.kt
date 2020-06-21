package com.example.tortoise.apis.skyscanner

import com.google.gson.annotations.SerializedName

data class Carriers (

	@SerializedName("CarrierId") val carrierId : Int,
	@SerializedName("Name") val name : String
)