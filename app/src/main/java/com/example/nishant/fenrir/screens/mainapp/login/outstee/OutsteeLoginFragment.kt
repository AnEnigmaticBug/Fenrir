package com.example.nishant.fenrir.screens.mainapp.login.outstee

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_outstee_login.*
import kotlinx.android.synthetic.main.fra_outstee_login.view.*

class OutsteeLoginFragment : Fragment() {

    private lateinit var viewModel: OutsteeLoginViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootPOV = inflater.inflate(R.layout.fra_outstee_login, container, false)
        viewModel = ViewModelProviders.of(this, OutsteeLoginViewModelFactory())[OutsteeLoginViewModel::class.java]

        rootPOV.loginBTN.setOnClickListener {
            viewModel.login(usernameTXT.text.toString(), passwordTXT.text.toString())
        }

        viewModel.loginStatus.observe(this, Observer {
            when(it!!) {
                is LoginAttemptStatus.Idle -> showInProgressStuff(false)
                is LoginAttemptStatus.InProgress -> showInProgressStuff(true)
                is LoginAttemptStatus.Success -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    showInProgressStuff(false)
                    findNavController().navigate(R.id.profileFragment)
                }
                is LoginAttemptStatus.Failure -> {
                    rootPOV.messageLBL.text = "Error: ${(it as LoginAttemptStatus.Failure).message}"
                    showInProgressStuff(false)
                }
            }
        })

        return rootPOV
    }

    private fun showInProgressStuff(show: Boolean) {
        val visibility = when(show) {
            true  -> View.VISIBLE
            false -> View.GONE
        }
        rootPOV.screenFaderPOV.visibility = visibility
        rootPOV.inProgressLOT.visibility = visibility
    }
}