        package io.eskills.Retrofit


import AddAnswerAnswer
import AddQuestionAnswer
import AddQuestionBody
import AllCoursesResponse
import CoursesListResponse
import MessagingResponse
import ProfileResponse
import ProjectResponse
import QuestionResponse
import SingleQuestionResponse
import StackOverFlowAnswer
import TrackingResponse
import io.eskills.Models.*
import io.eskills.Models.CoursesListResponse.SingleCourseResponse
import retrofit2.Call
import retrofit2.http.*

interface INodeJS {

    @POST("api/auth/login")
    fun loginUser(
        @Body loginBody: LoginBody,
    ): Call<LoginResponse>

    @POST("api/auth/request_account")
    fun askForAccountUser(
        @Body askForAccountBody: AskForAccountBody,
    ): Call<AskForAccountResponse>

    @PATCH("api/user//updateAccountMobile")
    fun updateAccountUser(
        @Body updateAccountBody: UpdateAccountBody,
    ): Call<AllDetailsResponse>

    @POST("api/questions/addQuestion")
    fun addQuestion(
        @Body addQuestionBody: AddQuestionBody,
    ): Call<AddQuestionAnswer>

    @POST("api/questions/addAnswer")
    fun addAnswer(
        @Body addQuestionBody: AddAnswerBody,
    ): Call<ArrayList<AddAnswerAnswer>>

    @POST("api/questions/voteQuestion")
    fun voteAnswer(
        @Body voteBody: VoteBody,
    ): Call<Void>

    @POST("api/questions/viewQuestion")
    fun viewQuestion(
        @Body viewQuestionBody: ViewQuestionBody
    ): Call<ViewQuestionAnswer>

    @GET("api/user/profile/all")
    fun getAllDetails(): Call<AllDetailsResponse>

    @GET("api/user/availableCoursesDetails")
    fun getCourses(): Call<ArrayList<AllCoursesResponse>>

    @GET("api/courses/getAllCourses")
    fun getCoursesList(): Call<CoursesListResponse>

    @GET("/api/user/availableCourses")
    fun getAvailableCourses(): Call<CoursesListResponse>

    @GET("/api/courses/getACourse/{id}")
    fun getAcourse(@Path("id") id: String): Call<SingleCourseResponse>

    @GET("/api/projects/getProjects")
    fun getProjects(): Call<ArrayList<ProjectResponse>>

    @GET("/api/messaging/allUsers")
    fun getMessages(): Call<ArrayList<MessagingResponse>>

    @GET("/api/questions/getAllQuestions/oldest/{pagenumber}")
    fun getAllQuestions(@Path("pagenumber") pagenumber: Int): Call<QuestionResponse>

    @GET("/api/questions/getQuestion/{id}")
    fun getQuestion(@Path("id") id: String): Call<SingleQuestionResponse>

    @GET("/api/user/getProfile/{id}")
    fun getProfile(@Path("id") id: String): Call<ProfileResponse>

    @GET("/api/user/quiz/{id}")
    fun getTracking(@Path("id") id: String): Call<TrackingResponse>

    @GET("/2.2/search/advanced")
    fun getStackOverFlow(
        @Query("page") page: Int,
        @Query("order") order: String,
        @Query("pagesize") pagesize: Int,
        @Query("sort") sort: String,
        @Query("title") title: String,
        @Query("site") site: String,
    ): Call<StackOverFlowAnswer>

}