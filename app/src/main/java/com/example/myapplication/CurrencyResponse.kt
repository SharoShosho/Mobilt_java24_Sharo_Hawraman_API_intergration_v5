package com.example.myapplication.data






data class CurrencyResponse(
    val amount: Double,  // The amount sent for conversion (e.g., 1)
    val base: String,    // The base currency (e.g., USD)
    val rates: Map<String, Double>  // Conversion rates for various currencies
)



data class ConversionResponse(
    val base_code: String,
    val target_code: String,
    val conversion_rate: Double,
    val conversion_result: Double


)
