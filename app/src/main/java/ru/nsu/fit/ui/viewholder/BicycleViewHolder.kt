package ru.nsu.fit.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.databinding.ItemBicycleBinding
import ru.nsu.fit.domain.model.SimpleBicycle

class BicycleViewHolder(
    private val binding: ItemBicycleBinding,
    private val notReadyForSaleMessage: String,
    private val onClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(bike: SimpleBicycle) {
        with(binding) {
            bikeNameText.text = bike.name
            sellingPriceText.text = if (bike.sellingPrice != null) String.format(
                sellingPriceText.text.toString(),
                bike.sellingPrice
            ) else notReadyForSaleMessage
            tiresText.text = bike.wheelSize.diameter.toString()
            bike.picture?.also { photo ->
                binding.bikeImage.setImageBitmap(photo)
            }
            card.setOnClickListener { onClickListener(bike.id) }
        }
    }

}