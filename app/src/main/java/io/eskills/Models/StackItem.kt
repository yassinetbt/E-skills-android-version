package io.eskills.Models

import Answers

class StackItem {
    var id_question: String? = null
    var stack_profile_image: String? = null
    var stack_profile_name: String? = null
    var stack_profile_time: String? = null
    var stack_title: String? = null
    var stack_question: String? = null
    var votes_number: String? = null
    var replies_number: String? = null
    var views_number: String? = null
    var responses: List<Answers>? = null
    var url: String? = null
    var voted: Boolean? = null


    constructor(
        id_question: String?,
        stack_profile_image: String?,
        stack_profile_name: String?,
        stack_profile_time: String?,
        stack_title: String?,
        stack_question: String?,
        votes_number: String?,
        replies_number: String?,
        views_number: String?,
        url: String?,

        ) {
        this.id_question = id_question
        this.stack_profile_image = stack_profile_image
        this.stack_profile_name = stack_profile_name
        this.stack_profile_time = stack_profile_time
        this.stack_title = stack_title
        this.stack_question = stack_question
        this.votes_number = votes_number
        this.replies_number = replies_number
        this.views_number = views_number
        this.url = url
    }

    constructor(
        id_question: String?,
        stack_profile_time: String?,
        stack_title: String?,
        stack_question: String?,
        votes_number: String?,
        replies_number: String?,
        views_number: String?,
        responses: List<Answers>? = null,
        voted: Boolean,
    ) {
        this.id_question = id_question
        this.stack_profile_time = stack_profile_time
        this.stack_title = stack_title
        this.stack_question = stack_question
        this.votes_number = votes_number
        this.replies_number = replies_number
        this.views_number = views_number
        this.responses = responses
        this.voted = voted

    }


}