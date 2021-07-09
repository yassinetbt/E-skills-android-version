package io.eskills.Views.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import io.eskills.databinding.FragmentTitleQuestionBinding


class TitleQuestionFragment : Fragment() {
    private var binding: FragmentTitleQuestionBinding? = null
    var questionTitle: EditText? = null
    var questionTags: EditText? = null
    var questionText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTitleQuestionBinding.inflate(layoutInflater)
        val view = binding?.root

        questionTitle = binding?.questionTitle
        questionTags = binding?.questionTags
        questionText = binding?.questionContentText

        binding?.questionTitle?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding?.questionTitle?.text.toString().length < 4) {
                    binding?.questionTitle?.error = "At least 5 characters required"
                }
            }

        })
        return view
    }

}