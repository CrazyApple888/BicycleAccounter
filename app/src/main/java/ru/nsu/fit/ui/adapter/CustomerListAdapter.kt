package ru.nsu.fit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.databinding.ItemCustomerBinding
import ru.nsu.fit.domain.model.SimpleCustomer
import ru.nsu.fit.ui.viewholder.CustomerViewHolder

class CustomerListAdapter(
    private val onCLickListener: (Int) -> Unit
) : RecyclerView.Adapter<CustomerViewHolder>() {

    var data = emptyList<SimpleCustomer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val itemBinding =
            ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(itemBinding, onCLickListener)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size
}
