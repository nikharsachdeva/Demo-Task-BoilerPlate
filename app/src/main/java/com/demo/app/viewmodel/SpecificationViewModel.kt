package com.demo.app.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.app.model.DataModel
import com.demo.app.model.MusicModel
import com.demo.app.repository.HomeRepository
import com.demo.app.repository.SpecificationRepository
import com.demo.app.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificationViewModel @Inject constructor(private val repository: SpecificationRepository) :
    ViewModel() {

    private val _dataModel = MutableLiveData<DataModel?>()
    val dataModel: LiveData<DataModel?> get() = _dataModel

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun loadDataFromJson(context: Context, fileName: String) {
        _loading.value = true

        viewModelScope.launch {
            try {
                val result = repository.getDataModel(context, fileName)
                _dataModel.postValue(result)
            } catch (e: Exception) {
                // Handle exception
            } finally {
                _loading.postValue(false)
            }
        }
    }

}