package com.example.nishant.fenrir.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.nishant.fenrir.screens.mainapp.events.eventfilter.eventfilterlist.EventFilterListFragment
import com.example.nishant.fenrir.screens.mainapp.events.eventfilter.eventfiltermenu.EventFilterMenuFragment
import com.example.nishant.fenrir.screens.wallet.items.addtocart.AddToCartFragment
import com.example.nishant.fenrir.screens.wallet.money.addmoney.AddMoneyFragment

abstract class NavHostFragment : Fragment(), NavigationHost {

    abstract val navViewId: Int

    override fun show(destination: Destination, bundle: Bundle?) {
        val fragment = when(destination) {
            NavigationGraph.MainApp.Events.EventFilter.FILTER_MENU -> EventFilterMenuFragment().withBundle(bundle)
            NavigationGraph.MainApp.Events.EventFilter.FILTER_LIST -> EventFilterListFragment().withBundle(bundle)
            NavigationGraph.Wallet.Items.ADD_TO_CART               -> AddToCartFragment().withBundle(bundle)
            NavigationGraph.Wallet.Money.ADD_MONEY                 -> AddMoneyFragment().withBundle(bundle)
            else                                                   -> throw IllegalArgumentException("Can't navigate to ${destination.name}")
        }
        childFragmentManager.beginTransaction()
                .setCustomAnimations(destination.ei, destination.eo, destination.pi, destination.po)
                .replace(navViewId, fragment, destination.name)
                .addToBackStack(null)
                .commit()
    }

    override fun back(): Boolean {
        if(childFragmentManager.backStackEntryCount == 0) {
            return false
        }
        if(childFragmentManager.backStackEntryCount == 1) {
            exit()
            return true
        }
        childFragmentManager.popBackStack()
        return true
    }

    override fun exit() {
        for(i in 0..childFragmentManager.backStackEntryCount) {
            childFragmentManager.popBackStack()
        }
    }
}