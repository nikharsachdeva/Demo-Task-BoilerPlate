package com.demo.app.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicModel(
    @SerializedName("data")
    var `data`: List<Data?>?,
    @SerializedName("endDate")
    var endDate: String?,
    @SerializedName("meditationState")
    var meditationState: MeditationState?,
    @SerializedName("startDate")
    var startDate: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("subscription")
    var subscription: String?,
    @SerializedName("subscriptionThrough")
    var subscriptionThrough: String?,
    @SerializedName("subscriptionType")
    var subscriptionType: String?
): Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("audioFile")
        var audioFile: String?,
        @SerializedName("audioType")
        var audioType: String?,
        @SerializedName("createdAt")
        var createdAt: String?,
        @SerializedName("fav")
        var fav: Boolean?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("idex")
        var idex: Int?,
        @SerializedName("imageFile")
        var imageFile: String?,
        @SerializedName("minute")
        var minute: String?,
        @SerializedName("paidFree")
        var paidFree: String?,
        @SerializedName("second")
        var second: String?,
        @SerializedName("subTitle")
        var subTitle: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("updatedAt")
        var updatedAt: String?
    ): Parcelable

    @Parcelize
    data class MeditationState(
        @SerializedName("currentStreak")
        var currentStreak: Int?,
        @SerializedName("customerId")
        var customerId: Int?,
        @SerializedName("lastPlayed")
        var lastPlayed: String?,
        @SerializedName("longestStreak")
        var longestStreak: Int?,
        @SerializedName("minuteMeditated")
        var minuteMeditated: Int?,
        @SerializedName("stateId")
        var stateId: Int?,
        @SerializedName("todayMinutes")
        var todayMinutes: Int?,
        @SerializedName("totalSessions")
        var totalSessions: Int?,
        @SerializedName("updatedAt")
        var updatedAt: String?,
        @SerializedName("userDateTime")
        var userDateTime: String?
    ) : Parcelable
}