package io.eskills.Views.Fragments

import Chapters
import TrackingResponse
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import io.eskills.Models.CoursesListResponse.SingleCourseResponse
import io.eskills.Models.Question
import io.eskills.R
import io.eskills.Retrofit.RetrofitClient
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentQuiz2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizFragment2(title: String) : Fragment(), View.OnClickListener {

    private var binding: FragmentQuiz2Binding? = null
    private var questions: ArrayList<Question>? = null
    private var rightQuestions: ArrayList<String>? = null
    private var userAnswers: ArrayList<String>? = null
    private var isCorrect: ArrayList<Boolean>? = null
    private val chapterTitle = title
    private val TAG = "xxx"
    var required = true
    private var preferences: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuiz2Binding.inflate(layoutInflater)
        val view = binding?.root
        preferences = activity?.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        questions = ArrayList()
        binding?.quizScrollView?.isVerticalScrollBarEnabled = false
        binding?.root?.visibility = View.INVISIBLE
        val allChaptersList: List<Chapters> =
            Gson().fromJson(preferences?.getString("chapters", ""),
                SingleCourseResponse::class.java).requests.chapters
        val chapter = allChaptersList.find { it.title == chapterTitle }
        if (chapter?.quiz?.questions == null) {
            Toast.makeText(context, "No questions available", Toast.LENGTH_LONG).show()
        } else {
            getTracking(chapter.quiz._id)
            for (question in chapter.quiz.questions) {
                questions?.add(Question(question.question,
                    question.answers[0],
                    question.answers[1],
                    question.answers[2],
                    question.answers[3],
                    question.index))
            }
        }

        binding?.quiz1?.setOnClickListener(this)
        binding?.quiz2?.setOnClickListener(this)
        binding?.quiz3?.setOnClickListener(this)
        binding?.quiz4?.setOnClickListener(this)
        binding?.quiz5?.setOnClickListener(this)
        binding?.quiz6?.setOnClickListener(this)
        binding?.quiz7?.setOnClickListener(this)
        binding?.quiz8?.setOnClickListener(this)
        binding?.quiz9?.setOnClickListener(this)
        binding?.quiz10?.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        if (v == binding?.quiz1) {
            if (!required) {
                setQuestion(questions!![0], 1)
                if (isCorrect?.size!! >= 1) {
                    setQuestion(questions!![0],
                        isCorrect!![0],
                        rightQuestions!![0],
                        userAnswers!![0])
                }
            }

        }
        if (v == binding?.quiz2) {
            if (!required) {
                setQuestion(questions!![1], 2)
                if (isCorrect?.size!! >= 2) {
                    setQuestion(questions!![1],
                        isCorrect!![1],
                        rightQuestions!![1],
                        userAnswers!![1])
                }
            }
        }
        if (v == binding?.quiz3) {
            if (!required) {
                setQuestion(questions!![2], 3)
                if (isCorrect?.size!! >= 3) {
                    setQuestion(questions!![2],
                        isCorrect!![2],
                        rightQuestions!![2],
                        userAnswers!![2])
                }
            }
        }
        if (v == binding?.quiz4) {
            if (!required) {
                setQuestion(questions!![3], 4)
                if (isCorrect?.size!! >= 4) {
                    setQuestion(questions!![3],
                        isCorrect!![3],
                        rightQuestions!![3],
                        userAnswers!![3])
                }
            }
        }
        if (v == binding?.quiz5) {
            if (!required) {
                setQuestion(questions!![4], 5)
                if (isCorrect?.size!! >= 5) {
                    setQuestion(questions!![4],
                        isCorrect!![4],
                        rightQuestions!![4],
                        userAnswers!![4])
                }
            }
        }
        if (v == binding?.quiz6) {
            if (!required) {
                setQuestion(questions!![5], 6)
                if (isCorrect?.size!! >= 6) {
                    setQuestion(questions!![5],
                        isCorrect!![5],
                        rightQuestions!![5],
                        userAnswers!![5])
                }
            }
        }
        if (v == binding?.quiz7) {
            setQuestion(questions!![6], 7)
            if (!required) {
                if (isCorrect?.size!! >= 7) {
                    setQuestion(questions!![6],
                        isCorrect!![6],
                        rightQuestions!![6],
                        userAnswers!![6])
                }
            }
        }
        if (v == binding?.quiz8) {
            if (!required) {
                setQuestion(questions!![7], 8)
                if (isCorrect?.size!! >= 8) {
                    setQuestion(questions!![7],
                        isCorrect!![7],
                        rightQuestions!![7],
                        userAnswers!![7])
                }
            }
        }
        if (v == binding?.quiz9) {
            if (!required) {
                setQuestion(questions!![8], 9)
                if (isCorrect?.size!! >= 9) {
                    setQuestion(questions!![8],
                        isCorrect!![8],
                        rightQuestions!![8],
                        userAnswers!![8])
                }
            }
        }
        if (v == binding?.quiz10) {
            if (!required) {
                setQuestion(questions!![9], 10)
                if (isCorrect?.size!! >= 10) {
                    setQuestion(questions!![9],
                        isCorrect!![9],
                        rightQuestions!![9],
                        userAnswers!![9])
                }
            }
        }
    }

    private fun setQuestion(
        question: Question,
        b: Boolean,
        rightAnswer: String,
        userAnswer: String,
    ) {
        if (question.answer1 == rightAnswer) {
            binding?.choice1?.setBackgroundResource(R.drawable.question_right)
            binding?.tickline1?.setBackgroundResource(R.drawable.greenline)
            binding?.tick1?.setBackgroundResource(R.drawable.ic_ticked)
            binding?.question1?.setTextColor(view?.resources!!.getColor(
                R.color.white,
                ((activity as HomeActivity?)!!).theme
            ))
        }
        if (question.answer2 == rightAnswer) {
            binding?.choice2?.setBackgroundResource(R.drawable.question_right)
            binding?.tickline2?.setBackgroundResource(R.drawable.greenline)
            binding?.tick2?.setBackgroundResource(R.drawable.ic_ticked)
            binding?.question2?.setTextColor(view?.resources!!.getColor(
                R.color.white,
                ((activity as HomeActivity?)!!).theme
            ))
        }
        if (question.answer3 == rightAnswer) {
            binding?.choice3?.setBackgroundResource(R.drawable.question_right)
            binding?.tickline3?.setBackgroundResource(R.drawable.greenline)
            binding?.tick3?.setBackgroundResource(R.drawable.ic_ticked)
            binding?.question3?.setTextColor(view?.resources!!.getColor(
                R.color.white,
                ((activity as HomeActivity?)!!).theme
            ))
        }
        if (question.answer4 == rightAnswer) {
            binding?.choice4?.setBackgroundResource(R.drawable.question_right)
            binding?.tickline4?.setBackgroundResource(R.drawable.greenline)
            binding?.tick4?.setBackgroundResource(R.drawable.ic_ticked)
            binding?.question4?.setTextColor(view?.resources!!.getColor(
                R.color.white,
                ((activity as HomeActivity?)!!).theme
            ))
        }
        if (!b) {
            if (question.answer1 == userAnswer) {
                binding?.choice1?.setBackgroundResource(R.drawable.question_false)
                binding?.tickline1?.setBackgroundResource(R.drawable.white_line)
                binding?.tick1?.setBackgroundResource(R.drawable.ic_false_tick)
                binding?.question1?.setTextColor(view?.resources!!.getColor(
                    R.color.white,
                    ((activity as HomeActivity?)!!).theme
                ))
            }
            if (question.answer2 == userAnswer) {
                binding?.choice2?.setBackgroundResource(R.drawable.question_false)
                binding?.tickline2?.setBackgroundResource(R.drawable.white_line)
                binding?.tick2?.setBackgroundResource(R.drawable.ic_false_tick)
                binding?.question2?.setTextColor(view?.resources!!.getColor(
                    R.color.white,
                    ((activity as HomeActivity?)!!).theme
                ))
            }
            if (question.answer3 == userAnswer) {
                binding?.choice3?.setBackgroundResource(R.drawable.question_false)
                binding?.tickline3?.setBackgroundResource(R.drawable.white_line)
                binding?.tick3?.setBackgroundResource(R.drawable.ic_false_tick)
                binding?.question3?.setTextColor(view?.resources!!.getColor(
                    R.color.white,
                    ((activity as HomeActivity?)!!).theme
                ))
            }
            if (question.answer4 == userAnswer) {
                binding?.choice4?.setBackgroundResource(R.drawable.question_false)
                binding?.tickline4?.setBackgroundResource(R.drawable.white_line)
                binding?.tick4?.setBackgroundResource(R.drawable.ic_false_tick)
                binding?.question4?.setTextColor(view?.resources!!.getColor(
                    R.color.white,
                    ((activity as HomeActivity?)!!).theme))
            }
        }
    }

    private fun setQuestion(question: Question, n: Int) {
        binding?.questionNumber?.text = "$n/10"
        binding?.choice1?.setBackgroundResource(R.drawable.question_answer)
        binding?.tickline1?.setBackgroundResource(R.drawable.grayline)
        binding?.tick1?.setBackgroundResource(R.drawable.ic_notticked)
        binding?.choice2?.setBackgroundResource(R.drawable.question_answer)
        binding?.tickline2?.setBackgroundResource(R.drawable.grayline)
        binding?.tick2?.setBackgroundResource(R.drawable.ic_notticked)
        binding?.choice3?.setBackgroundResource(R.drawable.question_answer)
        binding?.tickline3?.setBackgroundResource(R.drawable.grayline)
        binding?.tick3?.setBackgroundResource(R.drawable.ic_notticked)
        binding?.choice4?.setBackgroundResource(R.drawable.question_answer)
        binding?.tickline4?.setBackgroundResource(R.drawable.grayline)
        binding?.tick4?.setBackgroundResource(R.drawable.ic_notticked)
        binding?.question1?.text = question.answer1
        binding?.question1?.setTextColor(view?.resources!!.getColor(
            R.color.black,
            ((activity as HomeActivity?)!!).theme
        ))
        binding?.question2?.text = question.answer2
        binding?.question2?.setTextColor(view?.resources!!.getColor(
            R.color.black,
            ((activity as HomeActivity?)!!).theme
        ))
        binding?.question3?.text = question.answer3
        binding?.question3?.setTextColor(view?.resources!!.getColor(
            R.color.black,
            ((activity as HomeActivity?)!!).theme
        ))
        binding?.question4?.text = question.answer4
        binding?.question4?.setTextColor(view?.resources!!.getColor(
            R.color.black,
            ((activity as HomeActivity?)!!).theme
        ))
        binding?.questionTopic?.text = question.question
    }

    private fun getTracking(id: String) {
        RetrofitClient.instanceConnected.getTracking(id).enqueue(object :
            Callback<TrackingResponse> {
            override fun onResponse(
                call: Call<TrackingResponse>,
                response: Response<TrackingResponse>,
            ) {
                if (response.code() == 200) {
                    required = response.body()?._id?.required!!
                    rightQuestions = ArrayList()
                    isCorrect = ArrayList()
                    userAnswers = ArrayList()
                    if (!required) {
                        binding?.root?.visibility = View.VISIBLE
                    }
                    for (userAnswer in response.body()?._id?.userAnswer!!) {
                        rightQuestions?.add(userAnswer.right_answer)
                        isCorrect?.add(userAnswer.isCorrect)
                        userAnswers?.add(userAnswer.user_answer)
                        if (userAnswer.isCorrect) {
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 0) {
                                binding?.quiz1?.background?.setTint(view?.resources!!.getColor(
                                    R.color.green_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz1?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 1) {
                                binding?.quiz2?.background?.setTint(view?.resources!!.getColor(
                                    R.color.green_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz2?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 2) {
                                binding?.quiz3?.background?.setTint(view?.resources!!.getColor(
                                    R.color.green_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz3?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 3) {
                                binding?.quiz4?.background?.setTint(view?.resources!!.getColor(
                                    R.color.green_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz4?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 4) {
                                binding?.quiz5?.background?.setTint(view?.resources!!.getColor(
                                    R.color.green_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz5?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 5) {
                                binding?.quiz6?.background?.setTint(view?.resources!!.getColor(
                                    R.color.green_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz6?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 6) {
                                binding?.quiz7?.background?.setTint(view?.resources!!.getColor(
                                    R.color.green_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz7?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 7) {
                                binding?.quiz8?.background?.setTint(view?.resources!!.getColor(
                                    R.color.green_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz8?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 8) {
                                binding?.quiz9?.background?.setTint(view?.resources!!.getColor(
                                    R.color.green_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz9?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 9) {
                                binding?.quiz10?.background?.setTint(view?.resources!!.getColor(
                                    R.color.green_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz10?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                        } else {
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 0) {
                                binding?.quiz1?.background?.setTint(view?.resources!!.getColor(
                                    R.color.red_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz1?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 1) {
                                binding?.quiz2?.background?.setTint(view?.resources!!.getColor(
                                    R.color.red_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz2?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 2) {
                                binding?.quiz3?.background?.setTint(view?.resources!!.getColor(
                                    R.color.red_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz3?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 3) {
                                binding?.quiz4?.background?.setTint(view?.resources!!.getColor(
                                    R.color.red_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz4?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 4) {
                                binding?.quiz5?.background?.setTint(view?.resources!!.getColor(
                                    R.color.red_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz5?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 5) {
                                binding?.quiz6?.background?.setTint(view?.resources!!.getColor(
                                    R.color.red_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz6?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 6) {
                                binding?.quiz7?.background?.setTint(view?.resources!!.getColor(
                                    R.color.red_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz7?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 7) {
                                binding?.quiz8?.background?.setTint(view?.resources!!.getColor(
                                    R.color.red_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz8?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 8) {
                                binding?.quiz9?.background?.setTint(view?.resources!!.getColor(
                                    R.color.red_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz9?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                            if (response.body()?._id?.userAnswer?.indexOf(userAnswer) == 9) {
                                binding?.quiz10?.background?.setTint(view?.resources!!.getColor(
                                    R.color.red_quiz,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                                binding?.quiz10?.setTextColor(view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                ))
                            }
                        }

                    }
                    setQuestion(questions!![0], 1)
                    if (isCorrect?.size!! >= 1) {
                        setQuestion(questions!![0],
                            isCorrect!![0],
                            rightQuestions!![0],
                            userAnswers!![0])
                    }
                }
            }


            override fun onFailure(call: Call<TrackingResponse>, t: Throwable) {
                binding?.root?.visibility = View.INVISIBLE
                Toast.makeText(context, "No questions available", Toast.LENGTH_LONG).show()
            }

        })
    }

}
