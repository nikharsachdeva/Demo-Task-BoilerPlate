package com.demo.app.retrofit

import com.demo.app.model.MusicModel
import retrofit2.Response
import retrofit2.http.*

interface DeliveryAPI {

    @POST("/api/getChooseMeditation")
    suspend fun getAllNotifications(@Query("customerId") customerId: String,@Query("key") key: String): Response<MusicModel>

}