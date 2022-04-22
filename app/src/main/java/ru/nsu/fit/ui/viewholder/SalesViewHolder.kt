package ru.nsu.fit.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.databinding.ItemSaleBinding
import ru.nsu.fit.domain.model.Sale
import ru.nsu.fit.domain.util.calendarToStringFormat
import ru.nsu.fit.domain.util.toStringFormat
import java.util.*

class SalesViewHolder(
    private val binding: ItemSaleBinding,
    private val onClick: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Sale) {
        with(binding) {
            bikeTitle.text = item.bicycle.name
            warranty.text = String.format(warranty.text.toString(), item.warrantyEndDate.toStringFormat())
            price.text = item.finalCost.toString()
            date.dateDay.text = item.date.get(Calendar.DAY_OF_MONTH).toString()
            date.dateMonthYear.text = item.date.toStringFormat()
            card.setOnClickListener { onClick(item.bicycle.id) }
        }
    }

}