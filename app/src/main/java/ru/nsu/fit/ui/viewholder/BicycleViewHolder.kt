package ru.nsu.fit.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.databinding.ItemBicycleBinding
import ru.nsu.fit.domain.model.Bicycle

class BicycleViewHolder(
    private val binding: ItemBicycleBinding,
    private val notReadyForSaleMessage: String,
    private val onClickListener: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(bike: Bicycle) {
        with(binding) {
            bikeNameText.text = bike.name
            priceText.text = if (bike.sellingPrice != null) String.format(
                priceText.text.toString(),
                bike.sellingPrice
            ) else notReadyForSaleMessage
            tiresText.text = bike.wheelSize.toString()
            bike.picture?.also { photo ->
                binding.bikeImage.setImageBitmap(photo)
            }
            card.setOnClickListener { onClickListener(bike.id) }
        }
    }

}