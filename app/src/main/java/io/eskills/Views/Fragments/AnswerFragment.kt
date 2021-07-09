package io.eskills.Views.Fragments

import AddAnswerAnswer
import Answers
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.eskills.Models.AddAnswerBody
import io.eskills.Models.StackItem
import io.eskills.Retrofit.RetrofitClient
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentAnswerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnswerFragment(private val stackItem: StackItem?) : Fragment(), View.OnClickListener {

    private var binding: FragmentAnswerBinding? = null
    var answers: ArrayList<Answers>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAnswerBinding.inflate(layoutInflater)
        val view = binding?.root
        binding?.addAnswerButton?.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        if (v == binding?.addAnswerButton) {
            if (binding?.answerContentText?.text.toString() == "" && binding?.answerContentCode?.text.toString() == "") {
                Toast.makeText(context,
                    "You have to fill CONTENT CODE or CONTENT CODE",
                    Toast.LENGTH_LONG).show()
            } else {
                RetrofitClient.instanceConnected
                    .addAnswer(AddAnswerBody(
                        stackItem?.id_question!!,
                        binding?.answerContentText?.text.toString(),
                        binding?.answerContentCode?.text.toString()
                    )).enqueue(object : Callback<ArrayList<AddAnswerAnswer>> {
                        override fun onResponse(
                            call: Call<ArrayList<AddAnswerAnswer>>,
                            response: Response<ArrayList<AddAnswerAnswer>>,
                        ) {
                            if (response.code() == 200) {
                                answers = ArrayList()
                                for (answer in response.body()!!) {
                                    answers!!.add(Answers(answer.accepted,
                                        answer.views,
                                        answer.votes,
                                        answer.voters,
                                        answer._id,
                                        answer.contentText,
                                        answer.contentCode,
                                        answer.owner,
                                        answer.answer_date))
                                }
                                stackItem.responses = answers
                                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                                builder.setTitle("Thank you for answer")
                                builder.setMessage("Your answer will be reviewed by mentor as soon as possible")
                                    .setCancelable(false)
                                    .setPositiveButton("OK",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            val activity = activity as HomeActivity
                                            activity.loadMainFragment(SingleQuestionFragment(
                                                stackItem))

                                        })
                                val alert: AlertDialog = builder.create()
                                alert.show()
                            } else {
                            }
                        }

                        override fun onFailure(
                            call: Call<ArrayList<AddAnswerAnswer>>,
                            t: Throwable,
                        ) {
                            Toast.makeText(context,
                                "Connection Error please try later",
                                Toast.LENGTH_LONG).show()
                        }

                    })
            }
        }
    }

}