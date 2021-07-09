package io.eskills.Views.Fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.google.gson.Gson
import io.eskills.Models.AllDetailsResponse
import io.eskills.Models.UpdateAccountBody
import io.eskills.Retrofit.RetrofitClient
import io.eskills.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment(), TextWatcher, View.OnClickListener {
    private var binding: FragmentProfileBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view = binding?.root
        var scroll = binding?.scrollProfile
        scroll?.post {
            scroll.fullScroll(View.FOCUS_DOWN)
        }
        RetrofitClient.instanceConnected.getAllDetails().enqueue(object : Callback<AllDetailsResponse>{
            override fun onResponse(
                call: Call<AllDetailsResponse>,
                response: Response<AllDetailsResponse>,
            ) {
                if (response.code()==200){
                    binding?.firstName?.setText(response.body()?.firstName)
                    binding?.lastName?.setText(response.body()?.lastName)
                    binding?.emailaddressProfile?.setText(response.body()?.email)
                    Glide.with(context!!).load("https://e-skills.io/api/user/profile/getAvatar/" +response.body()?.avatar)
                        .into(object : CustomTarget<Drawable?>() {
                            override fun onResourceReady(
                                resource: Drawable,
                                transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
                            ) {
                                binding?.profileImage?.background=resource
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {

                            }

                        })
                }
            }

            override fun onFailure(call: Call<AllDetailsResponse>, t: Throwable) {
            }
        })

        binding?.passwordVerify?.visibility = View.INVISIBLE
        binding?.scrollProfile?.isVerticalScrollBarEnabled = false
        binding?.passwordProfile?.addTextChangedListener(this)
        binding?.emailaddressProfile?.addTextChangedListener(this)
        binding?.updateAccount?.setOnClickListener(this)
        binding?.addImageLayout?.setOnClickListener(this)
        return view
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        if (binding?.passwordProfile?.text.toString() == "") {
            binding?.passwordVerify?.visibility = View.INVISIBLE
        } else {
            if (binding?.passwordProfile?.text.toString().length < 3) {
                binding?.passwordProfile?.error = "At least 3 characters"
                binding?.passwordVerify?.visibility = View.INVISIBLE
            } else {
                binding?.passwordVerify?.visibility = View.VISIBLE
            }

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding?.emailaddressProfile?.text.toString())
                .matches()
        ) {
            binding?.emailaddressProfile?.error = "Invalid Email Address"
        }
    }

    override fun onClick(v: View?) {
        if (v == binding?.updateAccount) {
            if (binding?.passwordProfile?.text.toString() != binding?.passwordVerify?.text.toString()) {
                Toast.makeText(context, "Please verify your new password", Toast.LENGTH_LONG)
                    .show()
            } else {
                val alert: AlertDialog.Builder = AlertDialog.Builder(context)
                alert.setTitle("Password")
                alert.setMessage("Enter Password")
                val edited = EditText(context)
                edited.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                alert.setView(edited)
                alert.setPositiveButton("Ok"
                ) { dialog, whichButton ->
                    val oldPassword = edited.text.toString()
                    RetrofitClient.instanceConnected.updateAccountUser(UpdateAccountBody(
                        binding?.firstName?.text.toString(),
                        binding?.lastName?.text.toString(),
                        binding?.emailaddressProfile?.text.toString(),
                        oldPassword,
                        binding?.passwordVerify?.text.toString()
                    )).enqueue(object : Callback<AllDetailsResponse> {
                        override fun onResponse(
                            call: Call<AllDetailsResponse>,
                            response: Response<AllDetailsResponse>,
                        ) {
                            if (response.code() == 200) {
                                Toast.makeText(context,
                                    "Update done successfully",
                                    Toast.LENGTH_LONG)
                                    .show()
                                dialog.dismiss()
                            } else {
                                Toast.makeText(context, "Wrong Password", Toast.LENGTH_LONG).show()

                            }
                        }

                        override fun onFailure(call: Call<AllDetailsResponse>, t: Throwable) {
                            Toast.makeText(context, "Connexion Error", Toast.LENGTH_LONG).show()
                        }

                    })
                }

                alert.setNegativeButton("Cancel"
                ) { dialog, whichButton ->
                    dialog.cancel()
                }

                alert.show()

            }
        }
        if (v == binding?.addImageLayout) {
            pickImage()
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 1000
        const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    }

    private fun pickImage() {
        if (ActivityCompat.checkSelfPermission(context!!,
                READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            binding?.profileImage?.setImageURI(data?.data)
        }
    }

}