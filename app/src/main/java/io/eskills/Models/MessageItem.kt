package io.eskills.Models

class MessageItem {
    var image: String? = null
    var message: String? = null
    var time: String? = null
    var name: String? = null

    constructor(
        image: String?,
        message: String?,
        time: String?,
        name: String?,
    ) {
        this.image = image
        this.message = message
        this.time = time
        this.name = name
    }
}