package io.eskills.Models

import com.google.gson.annotations.SerializedName

class AskForAccountBody(
    @SerializedName("email") val email: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
)