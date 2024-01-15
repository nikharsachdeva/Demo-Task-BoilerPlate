package com.demo.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.app.databinding.RowItemApartmentOptionsBinding
import com.demo.app.model.DataModel

class ApartmentAdapter(val listener: ApartmentSelection) :
    ListAdapter<DataModel.Specification.DataList, ApartmentAdapter.ApartmentViewHolder>(
        ComparatorDiffUtil()
    ) {

    private var checkedPosition = -1
    private var selectedServiceId: String? = null

    fun setSelectedServiceId(id: String) {
        selectedServiceId = id
        val position = currentList.indexOfFirst { it.id == id }
        if (position != -1) {
            setSelectedService(position)
            val selectedService = getItem(position)
            selectedService?.let {
                listener.apartmentSelected(it)
            }

        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentViewHolder {
        val binding =
            RowItemApartmentOptionsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ApartmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApartmentViewHolder, position: Int) {
        val coupon = getItem(position)
        coupon?.let {
            holder.bind(it, position)
        }

    }

    inner class ApartmentViewHolder(private val binding: RowItemApartmentOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(apartment: DataModel.Specification.DataList, position: Int) {

            val isSelected = checkedPosition == adapterPosition

            binding.itemApartmentRadioRb.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    if (!isSelected) {
                        setSelectedService(adapterPosition)
                        listener.apartmentSelected(apartment)
                    }
                }
            }

            binding.itemApartmentRadioRb.isChecked = isSelected

            binding.itemApartmentNameTv.text = apartment.name!![0].toString()
            binding.itemApartmentBasePriceTv.text = "₹${apartment.price}.00"

        }
    }

    private fun setSelectedService(position: Int) {
        notifyItemChanged(checkedPosition)
        checkedPosition = position
        notifyItemChanged(checkedPosition)
    }

    interface ApartmentSelection {
        fun apartmentSelected(apartment: DataModel.Specification.DataList)
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<DataModel.Specification.DataList>() {
        override fun areItemsTheSame(
            oldItem: DataModel.Specification.DataList,
            newItem: DataModel.Specification.DataList,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataModel.Specification.DataList,
            newItem: DataModel.Specification.DataList,
        ): Boolean {
            return oldItem == newItem
        }

    }
}