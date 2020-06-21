package com.example.tortoise.apis.skyscanner

import com.google.gson.annotations.SerializedName

data class SkyScannerAPI (

	@SerializedName("Quotes") val quotes : List<Quotes>,
	@SerializedName("Places") val places : List<Places>,
	@SerializedName("Carriers") val carriers : List<Carriers>,
	@SerializedName("Currencies") val currencies : List<Currencies>
)