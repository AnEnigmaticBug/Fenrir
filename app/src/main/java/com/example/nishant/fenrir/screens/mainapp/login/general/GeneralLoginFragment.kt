package com.example.nishant.fenrir.screens.mainapp.login.general

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_general_login.view.*

class GeneralLoginFragment : Fragment() {

    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootPOV = inflater.inflate(R.layout.fra_general_login, container, false)

        rootPOV.outsteeLoginBTN.setOnClickListener {
            findNavController().navigate(R.id.outsteeLoginFragment)
        }

        return rootPOV
    }
}