package io.eskills.Models.CoursesListResponse

import Requests
import com.google.gson.annotations.SerializedName

data class SingleCourseResponse(

    @SerializedName("requests") val requests: Requests,
)