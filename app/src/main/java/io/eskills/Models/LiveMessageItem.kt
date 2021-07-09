package io.eskills.Models

class LiveMessageItem {
    var live_profile_image: Int? = null
    var live_message: String? = null
    var typemessage: Int = 0

    constructor(live_profile_image: Int?, live_message: String?, typemessage: Int?) {
        this.live_profile_image = live_profile_image
        this.live_message = live_message
        this.typemessage = typemessage!!
    }
}