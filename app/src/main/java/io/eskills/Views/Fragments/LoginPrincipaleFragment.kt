package io.eskills.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.eskills.R
import io.eskills.databinding.FragmentLoginPrincipaleBinding


class LoginPrincipaleFragment : Fragment() {
    private var login: TextView? = null
    private var askforaccount: TextView? = null
    private var binding: FragmentLoginPrincipaleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginPrincipaleBinding.inflate(layoutInflater)
        val view = binding?.root
        login = binding?.loginPrincipaleFragment
        login?.setOnClickListener(View.OnClickListener {
            loadFragment(LoginFragment())
        })
        askforaccount = binding?.askForAccount
        askforaccount?.setOnClickListener(View.OnClickListener {
            loadFragment(SignUpFragment())
        })
        return view
    }

    private fun loadFragment(fragment: Fragment) {
        val fm = this.fragmentManager
        val fragmentTransaction = fm?.beginTransaction()
        fragmentTransaction?.setCustomAnimations(
            R.anim.go_up,
            R.anim.go_down,
            R.anim.go_up2,
            R.anim.go_down2
        )
        fragmentTransaction?.replace(R.id.framelayout, fragment)
        fragmentTransaction?.commit()
    }


}