package io.eskills.Models

import com.google.gson.annotations.SerializedName

data class VoteBody(
    @SerializedName("id") val id: String,
    @SerializedName("vote") val vote: String,
)
