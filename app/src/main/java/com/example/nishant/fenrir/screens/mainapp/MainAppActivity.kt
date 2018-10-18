package com.example.nishant.fenrir.screens.mainapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import androidx.navigation.*
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavHostFragment
import kotlinx.android.synthetic.main.act_main.*

class MainAppActivity : AppCompatActivity() {

    private lateinit var viewModel: MainAppViewModel
    private var currentTabIndex = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        viewModel = ViewModelProviders.of(this, MainAppViewModelFactory())[MainAppViewModel::class.java]

        setupBottomNav()
    }

    private fun setupBottomNav() {
        AHBottomNavigationAdapter(this, R.menu.mn_bottom_nav_main_app).setupWithBottomNavigation(bottomNavAHB)
        bottomNavAHB.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavAHB.accentColor = resources.getColor(R.color.vio03)
        bottomNavAHB.setCurrentItem(currentTabIndex, false)

        bottomNavAHB.setOnTabSelectedListener { position, wasSelected ->
            if(position == 2) {
                bottomNavAHB.setCurrentItem(currentTabIndex, false)
            }
            else {
                currentTabIndex = position
            }
            if(wasSelected) {
                Toast.makeText(applicationContext, "Already at the tab", Toast.LENGTH_SHORT).show()
            }
            else {
                val options = NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(R.id.eventListFragment, false).build()
                when(position) {
                    0    -> {
                        when(viewModel.shouldShowLogin.value!!) {
                            true  -> findNavController(R.id.navHostFRA).navigate(R.id.outsteeLoginFragment, null, options)
                            false -> findNavController(R.id.navHostFRA).navigate(R.id.profileFragment, null, options)
                        }
                    }
                    1    -> findNavController(R.id.navHostFRA).navigate(R.id.eventListFragment, null, options)
                    2    -> {
                        when(viewModel.shouldShowLogin.value!!) {
                            true  -> {
                                bottomNavAHB.setCurrentItem(0, false)
                                findNavController(R.id.navHostFRA).navigate(R.id.outsteeLoginFragment, null, options)
                            }
                            false -> findNavController(R.id.navHostFRA).navigate(R.id.walletActivity, null, options)
                        }
                    }
                    3    -> findNavController(R.id.navHostFRA).navigate(R.id.mapFragment, null, options)
                    4    -> findNavController(R.id.navHostFRA).navigate(R.id.moreFragment, null, options)
                    else -> throw IllegalStateException("$position th bottom nav tab was selected")
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        val currentFragment = navHostFRA.childFragmentManager.findFragmentById(R.id.navHostFRA)
        if(currentFragment is NavHostFragment && currentFragment.back()) {
            return
        }
        super.onBackPressed()
    }
}
