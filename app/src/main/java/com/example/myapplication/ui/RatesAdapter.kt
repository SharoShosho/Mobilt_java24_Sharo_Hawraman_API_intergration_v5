package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemRateBinding

// RateItemDiffCallback jämför om objekt har ändrats, och används av ListAdapter för att optimera prestanda
class RatesAdapter : ListAdapter<RateItem, RatesAdapter.RatesViewHolder>(RateItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val binding = ItemRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val rateItem = getItem(position)  // Hämta objektet från ListAdapter
        holder.bind(rateItem)
    }

    override fun getItemCount(): Int = currentList.size  // currentList är listan som ListAdapter hanterar

    // ViewHolder för att binda data till en enskild rad i RecyclerView
    class RatesViewHolder(private val binding: ItemRateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rateItem: RateItem) {
            binding.tvCurrency.text = rateItem.currency
            binding.tvRate.text = rateItem.rate.toString()
        }
    }

    // DiffCallback för att jämföra objekt och se om de har ändrats
    class RateItemDiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<RateItem>() {
        override fun areItemsTheSame(oldItem: RateItem, newItem: RateItem): Boolean {
            return oldItem.currency == newItem.currency  // Jämför om valutakoden är densamma
        }

        override fun areContentsTheSame(oldItem: RateItem, newItem: RateItem): Boolean {
            return oldItem == newItem  // Jämför om hela objektet är samma
        }
    }
}
