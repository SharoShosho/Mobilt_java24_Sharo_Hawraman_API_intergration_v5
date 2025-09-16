package com.example.myapplication.ui.convert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentConvertBinding

class ConvertFragment : Fragment() {
    private var _binding: FragmentConvertBinding? = null
    private val binding get() = _binding!!

    private val currencies = listOf("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "SEK", "NZD")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConvertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter

        binding.btnConvert.setOnClickListener {
            val amt = binding.etAmount.text.toString().toDoubleOrNull()
            if (amt != null && amt > 0) {
                convertCurrency(amt, binding.spinnerFrom.selectedItem.toString(), binding.spinnerTo.selectedItem.toString())
            } else {
                binding.etAmount.error = "Please enter a valid amount"
            }
        }
    }

    private fun convertCurrency(amount: Double, from: String, to: String) {
        binding.progressBar.visibility = View.VISIBLE

        val url = "https://unirateapi.com/api/convert" +
                "?api_key=8zDKuH1TTTyl2d9Ub6FGVO1VqtycgD0u9DQON5ardi05yEMDCTlwzJEsskIkK4BN" +
                "&from=$from&to=$to&amount=$amount&format=json"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    // ✅ Get converted amount from API response
                    val result = response.getDouble("result")

                    // ✅ Put into bundle for ResultFragment
                    val bundle = Bundle().apply {
                        putFloat("amount", result.toFloat())
                        putString("fromCurrency", from)
                        putString("toCurrency", to)

                    }

                    // ✅ Navigate with data
                    findNavController().navigate(R.id.action_convertFragment_to_resultFragment, bundle)
                    Toast.makeText(requireContext(), "Conversion successful", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Parsing error: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    binding.progressBar.visibility = View.GONE
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            })

        Volley.newRequestQueue(requireContext()).add(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
