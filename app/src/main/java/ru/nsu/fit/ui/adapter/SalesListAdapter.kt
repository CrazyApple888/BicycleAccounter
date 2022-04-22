package ru.nsu.fit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.databinding.ItemSaleBinding
import ru.nsu.fit.domain.model.Sale
import ru.nsu.fit.ui.viewholder.SalesViewHolder

class SalesListAdapter(
    private val onClick: (Int) -> Unit
): RecyclerView.Adapter<SalesViewHolder>() {

    var data: List<Sale> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesViewHolder {
        val itemSaleBinding = ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SalesViewHolder(itemSaleBinding, onClick)
    }

    override fun onBindViewHolder(holder: SalesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.count()
}