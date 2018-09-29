package com.example.nishant.fenrir.screens.wallet

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavHostFragment
import com.example.nishant.fenrir.screens.mainapp.MainAppActivity
import kotlinx.android.synthetic.main.act_wallet.*

class WalletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_wallet)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        AHBottomNavigationAdapter(this, R.menu.mn_bottom_nav_wallet).setupWithBottomNavigation(bottomNavAHB)
        bottomNavAHB.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavAHB.accentColor = resources.getColor(R.color.vio03)
        bottomNavAHB.setCurrentItem(0, false)

        bottomNavAHB.setOnTabSelectedListener { position, wasSelected ->
            if(wasSelected) {
                Toast.makeText(applicationContext, "Already at the tab", Toast.LENGTH_SHORT).show()
            }
            else {
                val options = NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(R.id.profileFragment, false).build()
                when(position) {
                    0    -> findNavController(R.id.navHostFRA).navigate(R.id.profileFragment, null, options)
                    1    -> Toast.makeText(applicationContext, "Transfer", Toast.LENGTH_SHORT).show()
                    2    -> {
                        val intent = Intent(this, MainAppActivity::class.java)
                        startActivity(intent.also { it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                        finish()
                    }
                    3    -> findNavController(R.id.navHostFRA).navigate(R.id.stallsFragment, null, options)
                    4    -> findNavController(R.id.navHostFRA).navigate(R.id.cartFragment, null, options)
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