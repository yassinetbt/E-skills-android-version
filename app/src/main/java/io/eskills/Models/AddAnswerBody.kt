package io.eskills.Models

import com.google.gson.annotations.SerializedName

data class AddAnswerBody(
    @SerializedName("id") val title: String,
    @SerializedName("contentText") val contentText: String,
    @SerializedName("contentCode") val contentCode: String,
)