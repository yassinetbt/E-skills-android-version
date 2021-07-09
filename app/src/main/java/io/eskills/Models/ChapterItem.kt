package io.eskills.Models

import Lectures

class ChapterItem {

    var play: Int? = null
    var chapters_title: String? = null
    var length: String? = null
    var tickline: Int? = null
    var tick: Int? = null
    var title: String? = null
    var lectures: List<Lectures>? = null

    constructor(
        play: Int?,
        chapters_title: String?,
        length: String?,
        tickline: Int?,
        tick: Int?,
        title: String?,
        lectures: List<Lectures>,
    ) {
        this.play = play
        this.chapters_title = chapters_title
        this.length = length
        this.tickline = tickline
        this.tick = tick
        this.title = title
        this.lectures = lectures
    }
}