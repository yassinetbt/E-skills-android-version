package io.eskills.Models.QuestionsResponse

import com.google.gson.annotations.SerializedName

data class Voters(
    @SerializedName("id") val id: String,
    @SerializedName("vote") val vote: String,
)