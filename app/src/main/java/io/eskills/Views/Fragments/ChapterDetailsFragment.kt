package io.eskills.Views.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.eskills.databinding.FragmentChapterDetailsBinding


class ChapterDetailsFragment(private var description: String, private var lecture: String) :
    Fragment() {
    private var binding: FragmentChapterDetailsBinding? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChapterDetailsBinding.inflate(layoutInflater)
        val view = binding?.root
        binding?.lectureTitle?.text = lecture
        binding?.lectureDescription?.text = description
        binding?.lectureScrollView?.isVerticalScrollBarEnabled = false
        return view
    }


}