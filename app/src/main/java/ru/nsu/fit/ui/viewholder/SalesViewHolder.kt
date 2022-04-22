package ru.nsu.fit.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.databinding.ItemSaleBinding
import ru.nsu.fit.domain.model.Sale
import ru.nsu.fit.domain.util.toStringFormat
import java.util.*

class SalesViewHolder(
    private val binding: ItemSaleBinding,
    private val onClick: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Sale) {
        with(binding) {
            bikeTitle.text = item.bicycle.name
            //todo add warranty to db
            //warranty.text = String.format(warranty.text.toString(), item.warrantyEndDate.toStringFormat())
            price.text = item.finalCost.toString()
            date.dateDay.text = item.saleDate.get(Calendar.DAY_OF_MONTH).toString()
            date.dateMonthYear.text = item.saleDate.toStringFormat()
            card.setOnClickListener { onClick(item.bicycle.id) }
        }
    }

}