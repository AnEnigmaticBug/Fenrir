package com.example.nishant.fenrir.screens.wallet

import android.os.Bundle
import android.widget.Toast
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavHostActivity
import com.example.nishant.fenrir.screens.wallet.profile.ProfileFragment
import kotlinx.android.synthetic.main.act_wallet.*

class WalletActivity : NavHostActivity() {

    override val navViewId = R.id.navHostFRM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_wallet)

        setupBottomNav()

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(navViewId, ProfileFragment())
                    .commit()
        }
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
                when(position) {
                    0    -> Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT).show()
                    1    -> Toast.makeText(applicationContext, "Transfer", Toast.LENGTH_SHORT).show()
                    2    -> Toast.makeText(applicationContext, "Main App", Toast.LENGTH_SHORT).show()
                    3    -> Toast.makeText(applicationContext, "Stalls", Toast.LENGTH_SHORT).show()
                    4    -> Toast.makeText(applicationContext, "Cart", Toast.LENGTH_SHORT).show()
                    else -> throw IllegalStateException("$position th bottom nav tab was selected")
                }
            }
            true
        }
    }
}