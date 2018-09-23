package com.example.nishant.fenrir.more

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavigationGraph
import com.example.nishant.fenrir.navigation.NavigationHost
import kotlinx.android.synthetic.main.fra_more.view.*

class MoreFragment : Fragment() {

    private lateinit var navigationHost: NavigationHost
    private lateinit var rootPOV: View

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        navigationHost = when(context) {
            is NavigationHost -> context
            else              -> throw ClassCastException("Not a NavigationHost")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootPOV = inflater.inflate(R.layout.fra_more, container, false)
        setupBottomNav()

        // TODO: Write the code for buttons in More screen.

        return rootPOV
    }

    private fun setupBottomNav() {
        AHBottomNavigationAdapter(this.requireActivity(), R.menu.mn_bottom_nav).setupWithBottomNavigation(rootPOV.bottomNavAHB)
        rootPOV.bottomNavAHB.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW;
        rootPOV.bottomNavAHB.accentColor = resources.getColor(R.color.vio03)
        rootPOV.bottomNavAHB.setCurrentItem(4, false)

        // TODO: Add real destinations to the BottomNav.
        rootPOV.bottomNavAHB.setOnTabSelectedListener { position, wasSelected ->
            if(wasSelected) {
                Toast.makeText(requireContext().applicationContext, "Already at the tab", Toast.LENGTH_SHORT).show()
            }
            else {
                when(position) {
                    0    -> Toast.makeText(requireContext().applicationContext, "Profile", Toast.LENGTH_SHORT).show()
                    1    -> navigationHost.show(NavigationGraph.Events.EVENT_LIST)
                    2    -> Toast.makeText(requireContext().applicationContext, "Wallet", Toast.LENGTH_SHORT).show()
                    3    -> Toast.makeText(requireContext().applicationContext, "Map", Toast.LENGTH_SHORT).show()
                    4    -> navigationHost.show(NavigationGraph.More.MORE)
                    else -> throw IllegalStateException("$position th bottom nav tab was selected")
                }
                rootPOV.bottomNavAHB.setCurrentItem(position, false)
            }
            true
        }
    }
}