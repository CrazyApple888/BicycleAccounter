package ru.nsu.fit.domain.util

import androidx.recyclerview.widget.DiffUtil
import ru.nsu.fit.domain.model.SimpleBicycle

class SimpleBicycleDiffUtil (
    private val oldList: List<SimpleBicycle>,
    private val newList: List<SimpleBicycle>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int =
        oldList.count()

    override fun getNewListSize(): Int =
        newList.count()

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}