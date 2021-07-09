package io.eskills.Views

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.eskills.Retrofit.RetrofitClient
import io.eskills.databinding.*


@Suppress("DEPRECATION")
class Splash_Screen : AppCompatActivity() {
    private var binding: ActivitySplashScreenBinding? = null
    private val TAG = "xxx"
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var token: String? = null
    private var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
        if (!isConnectingToInternet()) {
            showAlertDialog(this, "No Internet Connection",
                "Please check your internet connection.", false)
        } else {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            preferences = getSharedPreferences(TAG, Context.MODE_PRIVATE)
            editor = preferences?.edit()
            token = preferences?.getString("token", "")
            val isconnected = preferences?.getBoolean("isconnected", false)
            if (isconnected == true) {
                val i = Intent(this@Splash_Screen, HomeActivity::class.java)
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
                    RetrofitClient.instanceConnected.getProjects().execute().body()?.size)
                i.putExtra("Projects",
                    gson.toJson(RetrofitClient.instanceConnected.getProjects()
                        .execute()
                        .body()))
                startActivity(i)
            } else {
                val i2 = Intent(this@Splash_Screen, MainActivity::class.java)
                startActivity(i2)
            }
        }

    }


    private fun isConnectingToInternet(): Boolean {
        val connectivity = applicationContext
            .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        for (i in info.indices) if (info[i].state == NetworkInfo.State.CONNECTED) {
            return true
        }
        return false
    }

    private fun showAlertDialog(
        context: Context?,
        title: String?,
        message: String?,
        status: Boolean?,
    ) {
        val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setButton("OK",
            { dialog, which -> finish() })
        alertDialog.show()
    }


// fun getToken(): String? {
//preferences =getSharedPreferences("xxx",Context.MODE_PRIVATE)
//var token = preferences?.getString("token", "")
//return token
//  return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmNjIzNTUxNTUxZGZlMDQwOWU4MWY4MiIsInR5cGUiOiJBIiwiaWF0IjoxNjEyNTE4OTAwfQ.iRR1GBm9A9dlS_UXnlg6bwjWsTW4I7gidZw8ptiO-IQ"
//}


}