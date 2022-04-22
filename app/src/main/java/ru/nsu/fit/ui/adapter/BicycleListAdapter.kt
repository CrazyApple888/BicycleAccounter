package ru.nsu.fit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.databinding.ItemBicycleBinding
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.SimpleBicycle
import ru.nsu.fit.domain.util.SimpleBicycleDiffUtil
import ru.nsu.fit.ui.viewholder.BicycleViewHolder

class BicycleListAdapter(
    private val notReadyForSaleMessage: String,
    private val onCLickListener: (Int) -> Unit
) : RecyclerView.Adapter<BicycleViewHolder>() {

    var data = emptyList<SimpleBicycle>()
        set(value) {
            val diff = SimpleBicycleDiffUtil(field, value)
            val results = DiffUtil.calculateDiff(diff)
            field = value
            results.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BicycleViewHolder {
        val itemBinding =
            ItemBicycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BicycleViewHolder(itemBinding, notReadyForSaleMessage, onCLickListener)
    }

    override fun onBindViewHolder(holder: BicycleViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.count()
}