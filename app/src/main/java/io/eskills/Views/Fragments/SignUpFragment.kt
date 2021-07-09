package io.eskills.Views.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.eskills.Models.AskForAccountBody
import io.eskills.Models.AskForAccountResponse
import io.eskills.R
import io.eskills.Retrofit.RetrofitClient
import io.eskills.databinding.FragmentSignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpFragment : Fragment(), View.OnClickListener, TextWatcher {

    private var login: TextView? = null
    private var askforaccount: TextView? = null

    private var binding: FragmentSignUpBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        val view = binding?.root
        login = binding?.loginSignup
        askforaccount = binding?.askforaccountButton
        login?.setOnClickListener(this)
        askforaccount?.setOnClickListener(this)
        binding?.emailaddress?.addTextChangedListener(this)
        binding?.firstname?.addTextChangedListener(this)
        binding?.lastname?.addTextChangedListener(this)
        binding?.phonenumber?.addTextChangedListener(this)
        return view
    }


    override fun onClick(v: View?) {
        if (v == login) {
            loadFragment(LoginFragment())
        } else {
            if (binding?.emailaddress?.text.toString() == "" ||
                binding?.firstname?.text.toString() == "" ||
                binding?.lastname?.text.toString() == "" ||
                binding?.phonenumber?.text.toString() == ""
            ) {
                Toast.makeText(context, "Please complete all fields", Toast.LENGTH_LONG).show()
            } else {
                RetrofitClient.instance
                    .askForAccountUser(AskForAccountBody(
                        binding?.emailaddress?.text.toString(),
                        binding?.firstname?.text.toString(),
                        binding?.lastname?.text.toString(),
                        binding?.phonenumber?.text.toString(),
                    )).enqueue(object : Callback<AskForAccountResponse> {
                        override fun onResponse(
                            call: Call<AskForAccountResponse>,
                            response: Response<AskForAccountResponse>,
                        ) {
                            if (response.code() == 200) {
                                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                                builder.setTitle("Thank you for joining us")
                                builder.setMessage("Your request for account is sent successfully \n An email will be sent for you in the next 24 hours")
                                    .setCancelable(false)
                                    .setPositiveButton("OK",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            loadFragment(LoginPrincipaleFragment())
                                        })
                                val alert: AlertDialog = builder.create()
                                alert.show()
                            } else {
                                Toast.makeText(context,
                                    "Your email is already used",
                                    Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<AskForAccountResponse>, t: Throwable) {
                            Toast.makeText(context,
                                "Connection Error please try later",
                                Toast.LENGTH_LONG).show()
                        }

                    })
            }
        }
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

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        if (!Patterns.EMAIL_ADDRESS.matcher(binding?.emailaddress?.text.toString())
                .matches()
        ) {
            binding?.emailaddress?.error = "Invalid Email Address"
            askforaccount?.isEnabled = false
        } else {
            askforaccount?.isEnabled = true
        }
        if ((binding?.firstname?.text.toString().length < 3)) {
            binding?.firstname?.error = "At least 3 characters long"
            askforaccount?.isEnabled = false
        } else {
            askforaccount?.isEnabled = true
        }
        if ((binding?.lastname?.text.toString().length < 3)) {
            binding?.lastname?.error = "At least 3 characters long"
            askforaccount?.isEnabled = false
        } else {
            askforaccount?.isEnabled = true
        }
        if (binding?.phonenumber?.text.toString().length != 8 || !Patterns.PHONE.matcher(
                binding?.phonenumber?.text.toString()).matches()
        ) {
            binding?.phonenumber?.error = "Invalid Phone Number"
            askforaccount?.isEnabled = false
        } else {
            askforaccount?.isEnabled = true
        }

    }


}