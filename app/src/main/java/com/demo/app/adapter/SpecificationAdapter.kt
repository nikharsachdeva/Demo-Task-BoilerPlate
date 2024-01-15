package com.demo.app.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.app.databinding.RowItemSpecificationBinding
import com.demo.app.model.DataModel
import com.google.gson.Gson

class SpecificationAdapter(val context: Context, val listener: SpecificationSelection) :
    ListAdapter<DataModel.Specification, SpecificationAdapter.SpecificationViewHolder>(
        ComparatorDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationViewHolder {
        val binding =
            RowItemSpecificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecificationViewHolder, position: Int) {
        val coupon = getItem(position)
        coupon?.let {
            holder.bind(it, position)
        }

    }

    inner class SpecificationViewHolder(private val binding: RowItemSpecificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(specification: DataModel.Specification, position: Int) {

            binding.itemSpecificationNameTv.text = specification.name!![0].toString()
            if (specification.maxRange == 0) {
                binding.itemSpecificationMaxRangeTv.text = "Choose 1"
            } else {
                binding.itemSpecificationMaxRangeTv.text = "Choose up to ${specification.maxRange}"
            }

            val specOptAdapter: SpecificationOptionAdapter = SpecificationOptionAdapter(
                binding.root.context,
                specification.list?.toMutableList() ?: mutableListOf(),
                object : SpecificationOptionAdapter.OnClickInterface {
                    override fun onOptionClicked(optionsList: MutableList<DataModel.Specification.DataList?>) {
                        Log.d("xoxo-->", "onOptionClicked: " + Gson().toJson(optionsList))
                    }


                })
            binding.itemSpecificationOptRv.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            binding.itemSpecificationOptRv.adapter = specOptAdapter


        }
    }

    interface SpecificationSelection {
        fun specificationSelected(specification: DataModel.Specification)
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<DataModel.Specification>() {
        override fun areItemsTheSame(
            oldItem: DataModel.Specification,
            newItem: DataModel.Specification,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataModel.Specification,
            newItem: DataModel.Specification,
        ): Boolean {
            return oldItem == newItem
        }

    }
}