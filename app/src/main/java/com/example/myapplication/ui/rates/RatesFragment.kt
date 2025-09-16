package com.example.myapplication.ui.rates

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.databinding.FragmentRatesBinding
import com.example.myapplication.data.RetrofitInstance
import com.example.myapplication.ui.RateItem
import com.example.myapplication.ui.RatesAdapter
import kotlinx.coroutines.launch

class RatesFragment : Fragment() {
    private var _binding: FragmentRatesBinding? = null
    private val binding get() = _binding!!
    private lateinit var ratesAdapter: RatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize adapter
        ratesAdapter = RatesAdapter()
        binding.rvRates.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ratesAdapter
        }
        loadExchangeRates2("USD")
        // Load exchange rates when fragment is created
        //loadExchangeRates("USD")  // Example: Get exchange rates for USD
    }
    private fun loadExchangeRates2(baseCurrency: String) {
        // Create a request queue
        val requestQueue = Volley.newRequestQueue(requireContext())

        // API URL with your API key
        val url = "https://unirateapi.com/api/rates?api_key=8zDKuH1TTTyl2d9Ub6FGVO1VqtycgD0u9DQON5ardi05yEMDCTlwzJEsskIkK4BN&format=json"

        // Create the request
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val ratesJsonObject = response.getJSONObject("rates")

                    // Convert the rates JSONObject into a list of items for the RecyclerView
                    val rateList = mutableListOf<Pair<String, Double>>()
                    val keys = ratesJsonObject.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        val value = ratesJsonObject.getDouble(key)
                        rateList.add(Pair(key, value))
                    }

                    // Sort the list by currency code
                    rateList.sortBy { it.first }
                    Log.i("SHARO", "loadExchangeRates: " + rateList.get(0))
                    // Update the RecyclerView adapter with the rates
                    ratesAdapter.submitList(rateList.map { RateItem(it.first, it.second) })
                } catch (e: Exception) {
                    Log.e("RatesFragment", "Error parsing response: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                Log.e("RatesFragment", "Error: ${error.message}")
            }
        )

        // Add the request to the request queue
        requestQueue.add(jsonRequest)
    }

    private fun loadExchangeRates(baseCurrency: String) {
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                val response = RetrofitInstance.api.getExchangeRates("USD", "EUR" +
                        "", 1.0)
                Log.i("SHARO", "loadExchangeRates: " +response.body())

               // if (response.isSuccessful && response.body() != null) {
                    val rates = response.body()!!.rates
                    val rateList = rates.map { (currency, rate) ->
                        RateItem(currency, rate)  // Map the rates to RateItem
                    }.sortedBy { it.currency }  // Sort the list by currency code

                    // Submit the list to the adapter
                    ratesAdapter.submitList(rateList)
               // }
            } catch (e: Exception) {
                // Handle the error
                Log.e("SHARO", e.message.toString())
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
