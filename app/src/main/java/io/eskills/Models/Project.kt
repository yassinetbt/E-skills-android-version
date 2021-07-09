package io.eskills.Models

class Project {
    var title: String? = null
    var link: String? = null
    var status: String? = null
    var technologies: String? = null
    var deadline: String? = null


    constructor(
        title: String?,
        link: String?,
        status: String?,
        technologies: String?,
        deadline: String?,
    ) {
        this.title = title
        this.link = link
        this.status = status
        this.technologies = technologies
        this.deadline = deadline
    }
}