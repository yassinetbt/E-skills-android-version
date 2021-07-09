package io.eskills.Views.Fragments

import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import io.eskills.Models.LoginBody
import io.eskills.Models.LoginResponse
import io.eskills.Retrofit.RetrofitClient
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment(), View.OnClickListener {
    private val TAG = "xxx"
    private var login: TextView? = null
    private var binding: FragmentLoginBinding? = null
    private var gson = Gson()
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    var facebookLogin: RelativeLayout?=null
    var googleLogin: RelativeLayout?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding?.root
        preferences = this.activity!!.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        editor = preferences?.edit()
        val email = preferences?.getString("email", "")
        val password = preferences?.getString("password", "")
        val token = preferences?.getString("token", "")
        binding?.editTextTextEmailAddress?.setText(email)
        binding?.editTextTextPassword?.setText(password)

        login = binding?.loginButton
        facebookLogin = binding?.facebookContainer
        googleLogin = binding?.googleContainer

        facebookLogin?.setOnClickListener(this)
        googleLogin?.setOnClickListener(this)
        login?.setOnClickListener(this)

        binding?.editTextTextEmailAddress?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!Patterns.EMAIL_ADDRESS.matcher(binding?.editTextTextEmailAddress?.text.toString())
                        .matches()
                ) {
                    binding?.editTextTextEmailAddress?.error = "Invalid Email Address"

                }
            }

        })
        return view
    }


    override fun onClick(v: View?) {
        if (v == login) {
            if (binding?.editTextTextEmailAddress?.text.toString() == "" || binding?.editTextTextPassword?.text.toString() == "") {
                Toast.makeText(context, "Please complete all fields", Toast.LENGTH_LONG).show()
            } else {
                RetrofitClient.instance
                    .loginUser(LoginBody(binding?.editTextTextEmailAddress?.text.toString(),
                        binding?.editTextTextPassword?.text.toString()))
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>,
                        ) {

                            if (response.code() == 200) {

                                editor?.putBoolean("isconnected",
                                    true)
                                editor?.putString("token", response.body()?.token.toString())
                                editor?.commit()
                                var i = Intent(context, HomeActivity::class.java)
                                i.putExtra("allCoursesResponse",
                                    gson.toJson(RetrofitClient.instanceConnected.getCourses()
                                        .execute().body()))
                                i.putExtra("allDetailsResponse",
                                    gson.toJson(RetrofitClient.instanceConnected.getAllDetails()
                                        .execute().body()))
                                i.putExtra("myCoursesResponse",
                                    gson.toJson(RetrofitClient.instanceConnected.getCoursesList()
                                        .execute().body()))
                                i.putExtra("allProjects",
                                    RetrofitClient.instanceConnected.getProjects().execute()
                                        .body()?.size)
                                i.putExtra("Projects",
                                    gson.toJson(RetrofitClient.instanceConnected.getProjects()
                                        .execute()
                                        .body()))
                                startActivity(i)
                            } else {
                                val errorResponse: LoginResponse = gson.fromJson(
                                    response.errorBody()!!.string(),
                                    LoginResponse::class.java)
                                Toast.makeText(context, errorResponse.error, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(context,
                                "Connection Error please try later",
                                Toast.LENGTH_LONG).show()
                        }

                    })
            }
        }
        if(v==facebookLogin){
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)//Lena hashti b token standard
                //to get information
                .requestIdToken("need token")
                .requestEmail()
                .build()
        }
        if(v==googleLogin){
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)//Lena hashti b token standard
                                                                                      //to get information
                .requestIdToken("dsdsds")
                .requestEmail()
                .build()
            

        }
    }


}