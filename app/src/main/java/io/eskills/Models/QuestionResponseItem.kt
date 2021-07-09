package io.eskills.Models

class QuestionResponseItem {
    var id: String? = null
    var stack_profile_image: String? = null
    var stack_profile_name: String? = null
    var stack_profile_time: String? = null
    var stack_question: String? = null
    var votes_number: String? = null
    var views_number: String? = null
    var accepted: Boolean? = null
    var voted: Boolean? = null

    constructor(
        id: String?,
        stack_profile_time: String?,
        stack_question: String?,
        votes_number: String?,
        views_number: String?,
        accepted: Boolean?,
        voted: Boolean?,
    ) {
        this.id = id
        this.stack_profile_time = stack_profile_time
        this.stack_question = stack_question
        this.votes_number = votes_number
        this.views_number = views_number
        this.accepted = accepted
        this.voted = voted
    }
}