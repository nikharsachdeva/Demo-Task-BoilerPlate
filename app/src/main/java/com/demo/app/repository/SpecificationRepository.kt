package com.demo.app.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.app.model.DataModel
import com.demo.app.model.MusicModel
import com.demo.app.retrofit.DeliveryAPI
import com.demo.app.utils.JsonFileReader
import com.demo.app.utils.NetworkResult
import javax.inject.Inject

class SpecificationRepository @Inject constructor() {
    suspend fun getDataModel(context: Context, fileName: String): DataModel? {
        return JsonFileReader.readJsonFile(context, fileName)
    }

}