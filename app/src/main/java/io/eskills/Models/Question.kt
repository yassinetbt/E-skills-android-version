package io.eskills.Models

class Question {
    var question: String? = null
    var answer1: String? = null
    var answer2: String? = null
    var answer3: String? = null
    var answer4: String? = null
    var index: Int? = null

    constructor(
        question: String?,
        answer1: String?,
        answer2: String?,
        answer3: String?,
        answer4: String?,
        index: Int?,
    ) {
        this.question = question
        this.answer1 = answer1
        this.answer2 = answer2
        this.answer3 = answer3
        this.answer4 = answer4
        this.index = index
    }
}