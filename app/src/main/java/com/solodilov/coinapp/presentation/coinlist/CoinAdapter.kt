package com.solodilov.coinapp.presentation.coinlist

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solodilov.coinapp.R
import com.solodilov.coinapp.databinding.ItemCoinBinding
import com.solodilov.coinapp.domain.entity.Coin
import java.util.*

class CoinAdapter(
    private val onClick: (Coin) -> Unit,
) : ListAdapter<Coin, CoinViewHolder>(CoinDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder =
        CoinViewHolder(ItemCoinBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false),
            onClick
        )

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CoinViewHolder(
    private val binding: ItemCoinBinding,
    private val onClick: (Coin) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coin: Coin) {

        binding.apply {
            coinName.text = coin.name
            coinSymbol.text = coin.symbol.uppercase()

            currentPrice.text = String.format(
                Locale.ENGLISH,
                itemView.context.getString(R.string.price_format),
                coin.currency.icon,
                coin.currentPrice,
            )

            priceChangePercentage.text = String.format(
                Locale.ENGLISH,
                itemView.context.getString(R.string.price_change_format),
                coin.priceChangePercentage,
            )
            val color = if (coin.priceChangePercentage >= 0) {
                R.color.green
            } else {
                R.color.red
            }
            priceChangePercentage.setTextColor(itemView.context.getColor(color))
        }

        Glide
            .with(itemView)
            .load(coin.image)
            .into(binding.coinImage)

        itemView.setOnClickListener { onClick(coin) }
    }
}

private class CoinDiffCallback : DiffUtil.ItemCallback<Coin>() {

    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }
}
