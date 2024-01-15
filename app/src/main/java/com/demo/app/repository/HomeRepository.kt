package com.demo.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.app.model.MusicModel
import com.demo.app.retrofit.DeliveryAPI
import com.demo.app.utils.NetworkResult
import javax.inject.Inject

class HomeRepository @Inject constructor(private val deliveryAPI: DeliveryAPI) {

    private val _allNotificationResponse = MutableLiveData<NetworkResult<MusicModel>>()
    val allNotificationResponse: LiveData<NetworkResult<MusicModel>>
        get() = _allNotificationResponse

    suspend fun getAllNotification(custId : String, key : String) {
        _allNotificationResponse.postValue(NetworkResult.Loading())
        val response = deliveryAPI.getAllNotifications(custId, key)
        if (response.isSuccessful && response.body() != null) {
            _allNotificationResponse.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _allNotificationResponse.postValue((NetworkResult.Error(response.code().toString())))
        } else {
            _allNotificationResponse.postValue((NetworkResult.Error("Something went wrong!!")))
        }

    }

}