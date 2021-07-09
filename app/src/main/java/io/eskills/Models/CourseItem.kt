package io.eskills.Models

class CourseItem {
    var id: String? = null
    var img: String? = null
    var title: String? = null
    var videos: String? = null
    var project: String? = null
    var saved: String? = null
    var state: String? = null
    var imageColor: Int? = null

    constructor(
        id: String?,
        img: String?,
        title: String?,
        videos: String?,
        project: String?,
        saved: String?,
        state: String?,
        imageColor: Int?,

        ) {
        this.id = id
        this.img = img
        this.title = title
        this.videos = videos
        this.project = project
        this.saved = saved
        this.state = state
        this.imageColor = imageColor

    }

}