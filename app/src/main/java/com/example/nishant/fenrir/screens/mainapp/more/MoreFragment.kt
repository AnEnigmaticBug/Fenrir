package com.example.nishant.fenrir.screens.mainapp.more

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavigationGraph
import com.example.nishant.fenrir.navigation.NavigationHost
import kotlinx.android.synthetic.main.fra_more.view.*

class MoreFragment : Fragment() {

    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootPOV = inflater.inflate(R.layout.fra_more, container, false)

        rootPOV.aboutBTN.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_aboutFragment)
        }

        rootPOV.sponsorsBTN.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_sponsorsFragment)
        }

        rootPOV.developersBTN.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_developersFragment)
        }

        rootPOV.n2oVotingBTN.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_n2OVotingFragment)
        }

        rootPOV.notificationsBTN.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_notificationsFragment)
        }

        return rootPOV
    }
}