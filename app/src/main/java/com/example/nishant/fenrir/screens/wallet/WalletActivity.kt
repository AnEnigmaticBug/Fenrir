package com.example.nishant.fenrir.screens.wallet

import android.os.Bundle
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavHostActivity
import com.example.nishant.fenrir.screens.wallet.profile.ProfileFragment

class WalletActivity : NavHostActivity() {

    override val navViewId = R.id.navHostFRM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_wallet)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(navViewId, ProfileFragment())
                    .commit()
        }
    }
}