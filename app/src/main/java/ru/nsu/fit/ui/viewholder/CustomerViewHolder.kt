package ru.nsu.fit.ui.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.databinding.ItemCustomerBinding
import ru.nsu.fit.domain.model.SimpleCustomer
import java.util.*

class CustomerViewHolder(
    private val binding: ItemCustomerBinding,
    private val onClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun onBind(customer: SimpleCustomer) {
        with(binding) {
            name.text = customer.name
            phoneNumber.text = customer.phone
            card.setOnClickListener { onClickListener(customer.id) }

            if (customer.lastTrade == null) {
                dateLayout.visibility = View.GONE
            } else {
                dateDay.text = customer.lastTrade.get(Calendar.DAY_OF_MONTH).toString()
                dateMonthYear.text = "${customer.lastTrade.get(Calendar.MONTH)} " +
                        "${customer.lastTrade.get(Calendar.YEAR)}"
            }
        }
    }
}