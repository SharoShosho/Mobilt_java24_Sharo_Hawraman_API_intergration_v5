package com.example.myapplication.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("api/rates?api_key=8zDKuH1TTTyl2d9Ub6FGVO1VqtycgD0u9DQON5ardi05yEMDCTlwzJEsskIkK4BN&format=json")
    suspend fun getExchangeRates(
         // API key as a query parameter
        @Query("from") baseCurrency: String,  // Base currency (e.g., USD)
        @Query("to") targetCurrency: String,  // Target currency (e.g., EUR)
        @Query("amount") amount: Double,   // Amount to convert

    ): Response<CurrencyResponse>  // Expected response type


    @GET("api/convert?api_key=8zDKuH1TTTyl2d9Ub6FGVO1VqtycgD0u9DQON5ardi05yEMDCTlwzJEsskIkK4BN&format=json")  // Correct endpoint without the full base URL
    suspend fun convertCurrency(
        @Query("from") fromCurrency: String,  // From currency
        @Query("to") toCurrency: String,  // To currency
        @Query("amount") amount: Double,   // Amount to convert
    ): Response<ConversionResponse>
}