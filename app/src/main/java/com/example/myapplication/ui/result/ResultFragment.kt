package com.example.myapplication.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentResultBinding
import java.text.DecimalFormat

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private var amount: Float = 0f
    private lateinit var fromCurrency: String
    private lateinit var toCurrency: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ Get data from bundle (sent by ConvertFragment)
        arguments?.let {
            amount = it.getFloat("amount", 0f)
            fromCurrency = it.getString("fromCurrency", "")
            toCurrency = it.getString("toCurrency", "")
        }

        // ✅ Format result
        val formatter = DecimalFormat("#,##0.00")
        val result = formatter.format(amount)

        // ✅ Show on screen
        binding.tvResult.text = "$result $toCurrency"
        binding.tvFromCurrency.text = fromCurrency
        binding.tvToCurrency.text = toCurrency

        // ✅ Buttons
        binding.btnNewConversion.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnHome.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
