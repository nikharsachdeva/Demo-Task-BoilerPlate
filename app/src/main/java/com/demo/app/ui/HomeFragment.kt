package com.demo.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.app.BaseFragment
import com.demo.app.adapter.notification.NotificationAdapter
import com.demo.app.databinding.FragmentHomeBinding
import com.demo.app.model.MusicModel
import com.demo.app.utils.NetworkResult
import com.demo.app.utils.makeViewGone
import com.demo.app.utils.makeViewVisible
import com.demo.app.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var notificationAdapter: NotificationAdapter
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNotificationRv()
        initObserver()
        homeViewModel.getAllNotification("379283", "All")
    }


    private fun initObserver() {
        homeViewModel.notificationLiveData.observe(viewLifecycleOwner, Observer {

            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    if (it.data?.status == "success" && it.data.data?.isNotEmpty() == true) {
                        setDataToRv(it.data)
                    }
                }

                is NetworkResult.Error -> {
                    getMainActivity()?.showSnackBar(it.message)
                }
            }
        })
    }

    private fun setDataToRv(data: MusicModel) {
        if (data.data.isNullOrEmpty()) {
            binding.notificationRv.makeViewGone()
            binding.notificationUnAvailableLl.makeViewVisible()
        } else {
            binding.notificationRv.makeViewVisible()
            binding.notificationUnAvailableLl.makeViewGone()
            notificationAdapter.submitList(data.data)
        }
    }

    private fun initNotificationRv() {

        notificationAdapter = NotificationAdapter(object : NotificationAdapter.OnClickInterface {
            override fun onNotificationClicked(doc: MusicModel.Data) {

            }
        })
        binding.notificationRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.notificationRv.adapter = notificationAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}