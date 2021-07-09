package io.eskills.Views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.eskills.R
import io.eskills.Views.Fragments.LoginPrincipaleFragment
import io.eskills.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        loadFragment(LoginPrincipaleFragment())
    }

    override fun onBackPressed() {

        var mFragmentManager = supportFragmentManager
        val frag: Fragment? = mFragmentManager.findFragmentById(R.id.framelayout)
        if (frag is LoginPrincipaleFragment) {
            val exitIntent = Intent(Intent.ACTION_MAIN)
            exitIntent.addCategory(Intent.CATEGORY_HOME)
            exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(exitIntent)
        } else {
            loadFragment(LoginPrincipaleFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.go_up,
            R.anim.go_down,
        )
        fragmentTransaction.replace(R.id.framelayout, fragment)
        fragmentTransaction.addToBackStack(null).commit()
    }

    override fun onClick(v: View?) {
    }
}