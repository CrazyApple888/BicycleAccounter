package ru.nsu.fit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.databinding.ItemSaleBinding
import ru.nsu.fit.domain.model.Sale
import ru.nsu.fit.ui.viewholder.SaleViewHolder

class SalesListAdapter(
    private val onClick: (Int) -> Unit
): RecyclerView.Adapter<SaleViewHolder>() {

    var data: List<Sale> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val itemSaleBinding = ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaleViewHolder(itemSaleBinding, onClick)
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.count()
}