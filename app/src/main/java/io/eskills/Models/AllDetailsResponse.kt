package io.eskills.Models

import com.google.gson.annotations.SerializedName

class AllDetailsResponse(
    @SerializedName("achievements_count") val achievements_count: Int,
    @SerializedName("stack_week_ach_count") val stack_week_ach_count: Int,
    @SerializedName("stack_ach_count") val stack_ach_count: Int,
    @SerializedName("projects_done") val projects_done: Int,
    @SerializedName("questions_Count") val questions_Count: Int,
    @SerializedName("rank") val rank: Int,
    @SerializedName("_id") val _id: String,
    @SerializedName("email") val email: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("last_checkpoint_chapters") val last_checkpoint_chapters: Last_checkpoint_chapters,
    @SerializedName("last_checkpoint_quiz") val last_checkpoint_quiz: Last_checkpoint_quiz,

    ) {


    class Last_checkpoint_chapters(
        @SerializedName("courseId") val courseId: String,
        @SerializedName("last_chapter_index") val last_chapter_index: Int,
        @SerializedName("last_chapter_id") val last_chapter_id: String,
        @SerializedName("last_lecture_index") val last_lecture_index: Int,
        @SerializedName("last_lecture_id") val last_lecture_id: String,
        @SerializedName("lecture_progress") val lecture_progress: Double,
        @SerializedName("chapters_count") val chapters_count: Int,

        )

    class Last_checkpoint_quiz(
        @SerializedName("quiz_id") val quiz_id: String,
        @SerializedName("question_index") val question_index: Int,
        @SerializedName("questions_count") val questions_count: Int,
    )


}
