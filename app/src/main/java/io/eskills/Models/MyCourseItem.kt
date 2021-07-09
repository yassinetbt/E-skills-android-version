package io.eskills.Models

class MyCourseItem {
    var id: String? = null
    var img: String? = null
    var title: String? = null
    var about: String? = null

    constructor(id: String?, img: String?, title: String?, about: String?) {
        this.id = id
        this.img = img
        this.title = title
        this.about = about
    }
}