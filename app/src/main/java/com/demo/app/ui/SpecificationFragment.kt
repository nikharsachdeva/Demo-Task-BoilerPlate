package com.demo.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.app.BaseFragment
import com.demo.app.adapter.ApartmentAdapter
import com.demo.app.adapter.SpecificationAdapter
import com.demo.app.databinding.FragmentSpecificationBinding
import com.demo.app.model.DataModel
import com.demo.app.viewmodel.SpecificationViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecificationFragment : BaseFragment() {

    private var _binding: FragmentSpecificationBinding? = null
    private val binding get() = _binding!!
    private val specsViewModel by viewModels<SpecificationViewModel>()
    private lateinit var apartmentAdapter: ApartmentAdapter
    private lateinit var specificationAdapter: SpecificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpecificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initApartmentRv()
        initSpecificationRv()
        fetchSpecificationData()
    }

    private fun initSpecificationRv() {
        specificationAdapter =
            SpecificationAdapter(requireContext(),
                object : SpecificationAdapter.SpecificationSelection {
                    override fun specificationSelected(specification: DataModel.Specification) {
                        Log.d(
                            "xoxo-->",
                            "specificationSelected: " + specification.name!![0].toString()
                        )
                    }


                })
        binding.specificationSelectionRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.specificationSelectionRv.adapter = specificationAdapter
    }

    private fun initApartmentRv() {

        apartmentAdapter = ApartmentAdapter(object : ApartmentAdapter.ApartmentSelection {
            override fun apartmentSelected(apartment: DataModel.Specification.DataList) {
                computeListAccordingToApartment(apartment)
                Log.d("xoxo-->", "apartmentSelected: " + apartment.name!![0].toString())
            }

        })
        binding.apartmentSelectionRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.apartmentSelectionRv.adapter = apartmentAdapter

    }

    private fun computeListAccordingToApartment(apartment: DataModel.Specification.DataList) {
        val allData = specsViewModel.dataModel.value
        if (allData != null) {
            val selectedApartmentId = apartment.id
            val servicesList = allData.specifications?.filter {
                it?.modifierId == selectedApartmentId
            }

            setDataToSpecificationRv(servicesList)
//            setDataToSpecificationRv(allData.specifications)
        }
    }

    private fun fetchSpecificationData() {
        // Trigger data loading
        specsViewModel.loadDataFromJson(requireContext(), "item_data.json")
    }

    private fun initObserver() {

        specsViewModel.dataModel.observe(viewLifecycleOwner, Observer { dataModel ->
            if (dataModel != null) {
                Log.d("xoxo-->", "initObserver: ----Data Read Successfully----")
                setDataToApartmentRv(dataModel)
            } else {
                Log.d("xoxo-->", "initObserver: ----Data Read Error----")
            }
        })

        specsViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                Log.d("xoxo-->", "initObserver: ----Loading Started----")
            } else {
                Log.d("xoxo-->", "initObserver: ----Loading Ended----")
            }
//            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

    }

    private fun setDataToApartmentRv(dataModel: DataModel) {

        binding.apartmentSelectionTitleTv.text =
            dataModel.specifications?.get(0)?.name!![0].toString()
        if (dataModel.specifications?.get(0)?.maxRange == 0) {
            binding.apartmentChoiceTitleTv.text = "Choose 1"
        } else {
            binding.apartmentChoiceTitleTv.text =
                "Choose up to ${dataModel.specifications?.get(0)?.maxRange}"
        }

        apartmentAdapter.submitList(dataModel.specifications?.get(0)?.list)

        val selectedApartment = dataModel.specifications?.get(0)?.list?.find {
            it?.isDefaultSelected == true
        }
        val selectedApartmentId = selectedApartment?.id

        if (selectedApartmentId != null) {
            apartmentAdapter.setSelectedServiceId(selectedApartmentId)
        }

    }

    private fun setDataToSpecificationRv(servicesList: List<DataModel.Specification?>?) {
        specificationAdapter.submitList(servicesList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}