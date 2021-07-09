package io.eskills.Views.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.eskills.databinding.FragmentSingleStackOverFlowQuestionBinding

class SingleStackOverFlowQuestionFragment(val url: String?) : Fragment() {
    private var binding: FragmentSingleStackOverFlowQuestionBinding? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSingleStackOverFlowQuestionBinding.inflate(layoutInflater)
        val view = binding?.root
        val webview = binding?.webView
        webview?.settings?.javaScriptEnabled = true
        webview?.loadUrl(url!!)
        return view
    }


}