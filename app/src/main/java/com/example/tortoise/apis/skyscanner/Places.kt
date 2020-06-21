package com.example.tortoise.apis.skyscanner

import com.google.gson.annotations.SerializedName

data class Places (

	@SerializedName("PlaceId") val placeId : Int,
	@SerializedName("Name") val name : String,
	@SerializedName("Type") val type : String,
	@SerializedName("SkyscannerCode") val skyscannerCode : String
)