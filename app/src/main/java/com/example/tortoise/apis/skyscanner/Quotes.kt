package com.example.tortoise.apis.skyscanner

import com.google.gson.annotations.SerializedName

data class Quotes (

	@SerializedName("QuoteId") val quoteId : Int,
	@SerializedName("MinPrice") val minPrice : Int,
	@SerializedName("Direct") val direct : Boolean,
	@SerializedName("OutboundLeg") val outboundLeg : OutboundLeg,
	@SerializedName("InboundLeg") val inboundLeg : InboundLeg? = null,
	@SerializedName("QuoteDateTime") val quoteDateTime : String
)