package com.example.nishant.fenrir.screens.mainapp.login.general

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.nishant.fenrir.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.fra_general_login.view.*

class GeneralLoginFragment : Fragment() {

    private lateinit var viewModel: GeneralLoginViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("912895203632-omblavpist6av2fqaq0q2n904ekj6nh5.apps.googleusercontent.com")
                .requestEmail()
                .requestProfile()
                .build())

        viewModel = ViewModelProviders.of(this, GeneralLoginViewModelFactory())[GeneralLoginViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_general_login, container, false)

        rootPOV.outsteeLoginBTN.setOnClickListener {
            findNavController().navigate(R.id.outsteeLoginFragment)
        }

        rootPOV.bitsianLoginBTN.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, 108)
        }

        return rootPOV
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 108) {
            try{
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
                Toast.makeText(requireContext(), account.displayName, Toast.LENGTH_LONG).show()
                viewModel.login(account.idToken!!)
            }
            catch(e: ApiException) {
                Toast.makeText(requireContext(), "${e.statusCode}: ${e.statusMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}