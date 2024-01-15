package com.demo.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.demo.app.databinding.ActivityMainBinding
import com.demo.app.ui.HomeFragment
import com.demo.app.utils.singleDateToDoubleDate
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.TimeZone

@AndroidEntryPoint
class MainActivity : ParentActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        onNewIntent(intent)
        //add Splash Screen
        addFragment(true, 1, HomeFragment())
    }



    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val bundle: Bundle? = intent?.extras
        if (bundle != null) {
            Log.d("xxyyzz", "onNewIntent: 1" + Gson().toJson(bundle))
        } else
            Log.d("xxyyzz", "onNewIntent: 2")
    }

    fun showSnackBar(message: String?) {
        hideKeyboard()
        Snackbar.make(binding.rlMainView, message!!, Snackbar.LENGTH_LONG).show()
    }


    fun updateFragments() {
        val fragment1: Fragment?
        val fragment2: Fragment?
        val fragment3: Fragment?
        val fragment4: Fragment?
        val fragment5: Fragment?
        val fm = supportFragmentManager
        val pos = getVisibleFrame()
        if (fragmentStack1.size > 0) {
            fragment1 = fm.findFragmentByTag(fragmentStack1[fragmentStack1.size - 1])
            if (fragment1 != null) {
                (fragment1 as BaseFragment).setFragmentContainerVisible(pos == 1)
                fragment1.onHiddenChanged(false)
            }
        }
        if (fragmentStack2.size > 0) {
            fragment2 = fm.findFragmentByTag(fragmentStack2[fragmentStack2.size - 1])
            if (fragment2 != null) {
                (fragment2 as BaseFragment).setFragmentContainerVisible(pos == 2)
                fragment2.onHiddenChanged(false)
            }
        }
        if (fragmentStack3.size > 0) {
            fragment3 = fm.findFragmentByTag(fragmentStack3[fragmentStack3.size - 1])
            if (fragment3 != null) {
                (fragment3 as BaseFragment).setFragmentContainerVisible(pos == 3)
                fragment3.onHiddenChanged(false)
            }
        }
        if (fragmentStack4.size > 0) {
            fragment4 = fm.findFragmentByTag(fragmentStack4[fragmentStack4.size - 1])
            if (fragment4 != null) {
                (fragment4 as BaseFragment).setFragmentContainerVisible(pos == 4)
                fragment4.onHiddenChanged(false)
            }
        }
        if (fragmentStack5.size > 0) {
            fragment5 = fm.findFragmentByTag(fragmentStack5[fragmentStack5.size - 1])
            if (fragment5 != null) {
                (fragment5 as BaseFragment).setFragmentContainerVisible(pos == 5)
                fragment5.onHiddenChanged(false)
            }
        }
    }

    fun getTodayStartDate(): String {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val day = calendar.get(Calendar.DAY_OF_MONTH).singleDateToDoubleDate()
        val month = (calendar.get(Calendar.MONTH) + 1).singleDateToDoubleDate()
        val year = calendar.get(Calendar.YEAR).singleDateToDoubleDate()

        return "${year}-${month}-${day}T00:00:00Z"
    }

    fun getTodayEndDate(): String {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val day = calendar.get(Calendar.DAY_OF_MONTH).singleDateToDoubleDate()
        val month = (calendar.get(Calendar.MONTH) + 1).singleDateToDoubleDate()
        val year = calendar.get(Calendar.YEAR).singleDateToDoubleDate()
        return "${year}-${month}-${day}T23:59:59Z"

    }

    override fun onBackPressed() {
        if (getVisibleFrameStakList()!!.size == 1 && getVisibleFrame() != 1) {
            clearBackStack(2)
            clearBackStack(3)
            clearBackStack(4)
            clearBackStack(5)
        } else if (getVisibleFrameStakList()!!.size == 1 && getVisibleFrame() == 1) {
            clearBackStack(0)
            finish()
        } else {
            removeCurrentFragment()

        }
    }

    override fun onClick(p0: View?) {
    }

}