package io.eskills.Views.Fragments

import AddQuestionAnswer
import AddQuestionBody
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import io.eskills.Adapters.CoursesViewPagerAdapter
import io.eskills.R
import io.eskills.Retrofit.RetrofitClient
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentAddQuestionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddQuestionFragment : Fragment(), View.OnClickListener {

    private var binding: FragmentAddQuestionBinding? = null
    private var adapter: CoursesViewPagerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddQuestionBinding.inflate(layoutInflater)
        val view = binding?.root
        adapter = CoursesViewPagerAdapter(childFragmentManager)
        adapter?.addFragment(TitleQuestionFragment(), " One ")
        adapter?.addFragment(ContentCodeFragment(), " Two ")
        adapter?.addFragment(PickImageFragment(), " Three ")
        binding?.questionViewPager?.adapter = adapter
        binding?.questionViewPager?.currentItem = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding?.title?.background?.setTint(
                view?.resources!!.getColor(
                    R.color.dashboardpurple,
                    ((activity as HomeActivity?)!!).theme
                )
            )
            binding?.title?.setTextColor(
                view?.resources!!.getColor(
                    R.color.white,
                    ((activity as HomeActivity?)!!).theme
                )
            )
            binding?.code?.background?.setTint(
                view?.resources!!.getColor(
                    R.color.white,
                    ((activity as HomeActivity?)!!).theme
                )
            )
            binding?.code?.setTextColor(
                view?.resources!!.getColor(
                    R.color.textdarkcolor,
                    ((activity as HomeActivity?)!!).theme
                )
            )
            binding?.pickImage?.background?.setTint(
                view?.resources!!.getColor(
                    R.color.white,
                    ((activity as HomeActivity?)!!).theme
                )
            )
            binding?.pickImage?.setTextColor(
                view?.resources!!.getColor(
                    R.color.textdarkcolor,
                    ((activity as HomeActivity?)!!).theme
                )
            )
        }
        binding?.questionViewPager?.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            binding?.title?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.dashboardpurple,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.title?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.code?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.code?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.pickImage?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.pickImage?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                        }

                    }
                    1 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            binding?.title?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.title?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.code?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.dashboardpurple,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.code?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.pickImage?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.pickImage?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                        }

                    }
                    2 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            binding?.title?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.title?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.code?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.code?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.pickImage?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.dashboardpurple,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            binding?.pickImage?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                        }

                    }

                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        binding?.addQuestionButton?.setOnClickListener(this)
        binding?.code?.setOnClickListener(this)
        binding?.title?.setOnClickListener(this)
        binding?.pickImage?.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        if (v == binding?.addQuestionButton) {
            val contentCodeFragment = adapter?.getItem(1) as ContentCodeFragment
            val titleQuestionFragment = adapter?.getItem(0) as TitleQuestionFragment
            val pickImageFragment = adapter?.getItem(2) as PickImageFragment
            if (titleQuestionFragment.questionTitle?.text.toString() == ""
            ) {
                Toast.makeText(context, "You have to fill title", Toast.LENGTH_LONG).show()
            } else if (titleQuestionFragment.questionText?.text.toString() == "") {
                Toast.makeText(context,
                    "You have to fill CONTENT TEXT",
                    Toast.LENGTH_LONG).show()
            } else {
                RetrofitClient.instanceConnected
                    .addQuestion(AddQuestionBody(
                        titleQuestionFragment.questionTitle?.text.toString(),
                        titleQuestionFragment.questionText?.text.toString(),
                        contentCodeFragment.questionCode?.text.toString(),
                        titleQuestionFragment.questionTags?.text.toString(),
                        pickImageFragment.image!!
                    )).enqueue(object : Callback<AddQuestionAnswer> {
                        override fun onResponse(
                            call: Call<AddQuestionAnswer>,
                            response: Response<AddQuestionAnswer>,
                        ) {
                            if (response.code() == 200) {
                                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                                builder.setTitle("Thank you for posting")
                                builder.setMessage("Your request will be approved by admin as soon as possible")
                                    .setCancelable(false)
                                    .setPositiveButton("OK",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            val activity = activity as HomeActivity
                                            activity.loadMainFragmentOnRightPosition(2)

                                        })
                                val alert: AlertDialog = builder.create()
                                alert.show()
                            } else {
                                Toast.makeText(context,
                                    "A question with same title already exists",
                                    Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<AddQuestionAnswer>, t: Throwable) {
                            Toast.makeText(context,
                                "Connection Error please try later",
                                Toast.LENGTH_LONG).show()
                        }

                    })
            }
        }


        if (v == binding?.title) {
            binding?.questionViewPager?.currentItem = 0
        }
        if (v == binding?.code) {
            binding?.questionViewPager?.currentItem = 1
        }
        if (v == binding?.pickImage) {
            binding?.questionViewPager?.currentItem = 2
        }
    }


}
