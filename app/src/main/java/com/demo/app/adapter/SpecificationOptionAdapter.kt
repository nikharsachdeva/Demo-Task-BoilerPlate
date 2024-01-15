package com.demo.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.app.databinding.RowItemSpecificationOptionsBinding
import com.demo.app.model.DataModel.Specification.DataList

class SpecificationOptionAdapter(
    val context: Context,
    var specOptList: MutableList<DataList?>,
    val onClickListener: OnClickInterface
) :
    RecyclerView.Adapter<SpecificationOptionAdapter.SelectServicesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectServicesViewHolder {
        val binding =
            RowItemSpecificationOptionsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SelectServicesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectServicesViewHolder, position: Int) {
        holder.bind(specOptList[position], position)

    }

    inner class SelectServicesViewHolder(private val binding: RowItemSpecificationOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(specOpt: DataList?, position: Int) {

            binding.itemSpecOptCheckboxCb.setOnCheckedChangeListener { buttonView, isChecked ->
                specOpt?.isOptionSelected = !specOpt?.isOptionSelected!!
                notifyDataSetChanged()
                onClickListener.onOptionClicked(specOptList)
            }

            binding.itemSpecOptNameTv.text = specOpt?.name!![0].toString()
            binding.itemSpecOptPriceTv.text = "₹${specOpt?.price}.00"

        }
    }

    fun getAll(): MutableList<DataList?> {
        return specOptList
    }

    fun addAll(serviceList: ArrayList<DataList>) {
        serviceList.clear()
        for (i in 0 until serviceList.size) {
            addSingle(serviceList[i])
        }
        notifyDataSetChanged()
    }

    private fun addSingle(data: DataList) {
        specOptList.add(data)
    }

    fun getSelected(): ArrayList<DataList?> {
        val selected: ArrayList<DataList?> = ArrayList()
        for (i in 0 until specOptList.size) {
            if (specOptList[i]?.isOptionSelected == true) {
                selected.add(specOptList[i])
            }
        }
        return selected
    }

    interface OnClickInterface {
        fun onOptionClicked(optionsList: MutableList<DataList?>)
    }

    override fun getItemCount(): Int {
        return specOptList.size
    }
}