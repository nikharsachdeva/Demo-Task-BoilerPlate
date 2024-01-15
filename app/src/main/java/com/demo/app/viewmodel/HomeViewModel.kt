package com.demo.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.app.model.MusicModel
import com.demo.app.repository.HomeRepository
import com.demo.app.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) :
    ViewModel() {

    val notificationLiveData: LiveData<NetworkResult<MusicModel>>
        get() = repository.allNotificationResponse

    fun getAllNotification(custId : String, key : String) {
        viewModelScope.launch {
            repository.getAllNotification(custId, key)
        }
    }

}