package io.eskills.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.eskills.Models.AllDetailsResponse
import io.eskills.Models.CoursesListResponse.SingleCourseResponse
import io.eskills.Retrofit.RetrofitClient
import io.eskills.databinding.FragmentCourseAboutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseAboutFragment(id: String, allDetailsResponse: AllDetailsResponse, allProjects: Int) :
    Fragment() {

    private var binding: FragmentCourseAboutBinding? = null
    private var id = id
    private var index = 0
    private var project = 0
    private var acheivements = 0
    private var quiz = 0
    private var questions = 0
    private var allDetailsResponse = allDetailsResponse
    private var allProjects = allProjects
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCourseAboutBinding.inflate(layoutInflater)
        val view = binding!!.root
        val scrollview = binding?.aboutscrollview
        scrollview?.isVerticalScrollBarEnabled = false
        getAllDetails()
        getAcourse()
        return view
    }

    private fun getAllDetails() {
        if (allDetailsResponse.last_checkpoint_chapters.courseId == id) {
            index = allDetailsResponse.last_checkpoint_chapters.last_chapter_index
            index++
        }
        project = allDetailsResponse.projects_done
        acheivements = allDetailsResponse.achievements_count
        quiz = allDetailsResponse.last_checkpoint_quiz.question_index
        questions = allDetailsResponse.last_checkpoint_quiz.questions_count

    }


    private fun getAcourse() {
        RetrofitClient.instanceConnected.getAcourse(this.id).enqueue(object :
            Callback<SingleCourseResponse> {
            override fun onResponse(
                call: Call<SingleCourseResponse>,
                response: Response<SingleCourseResponse>,
            ) {
                if (response.code() == 200) {
                    binding?.title?.text = response.body()?.requests?.title
                    val chapters = response.body()?.requests?.chapters_count!! - 1
                    binding?.chaptersnumber?.text = "" + index + "/" + chapters + " Chapters"
                    binding?.projectsnumber?.text = "$project/$allProjects Projects"
                    binding?.acheivments?.text = "$acheivements Achievements"
                    binding?.quiznumber?.text = "$quiz/$questions Quiz"
                    var progress =
                        (index.toDouble() / response.body()?.requests?.chapters_count!!) * 100
                    binding?.progressBar?.progress = progress.toInt()
                    binding?.textprogress?.text = "" + progress.toInt() + "%"
                    binding?.firstRequirement?.text = response.body()?.requests?.requirements?.get(
                        0)?.description
                    binding?.secondRequirement?.text = response.body()?.requests?.requirements?.get(
                        1)?.description
                    binding?.descriptionText?.text = response.body()?.requests?.description
                } else {

                }
            }

            override fun onFailure(call: Call<SingleCourseResponse>, t: Throwable) {

            }

        })

    }


}