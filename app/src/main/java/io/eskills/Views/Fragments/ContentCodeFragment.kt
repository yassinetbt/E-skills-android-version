package io.eskills.Views.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import io.eskills.Models.CodeEditText
import io.eskills.databinding.FragmentContentCodeBinding


class ContentCodeFragment : Fragment() {
    private var binding: FragmentContentCodeBinding? = null
    var questionCode: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContentCodeBinding.inflate(layoutInflater)
        val view = binding?.root
        questionCode = binding?.questionContentCode
        binding?.questionContentCode?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding?.questionContentCode?.removeTextChangedListener(this)
                val str: String = binding?.questionContentCode?.text.toString()

                val po: Int = binding?.questionContentCode?.selectionStart!! //get cursor

                val ss = CodeEditText.setHighLight(str)
                binding?.questionContentCode?.setText(ss)
                binding?.questionContentCode?.setSelection(po) //set cursor
                binding?.questionContentCode?.addTextChangedListener(this)

            }

        })
        return view
    }

}