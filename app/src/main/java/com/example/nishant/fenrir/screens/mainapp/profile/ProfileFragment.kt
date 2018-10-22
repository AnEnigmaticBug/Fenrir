package com.example.nishant.fenrir.screens.mainapp.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavHostFragment
import com.example.nishant.fenrir.navigation.NavigationGraph
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_profile.view.*

class ProfileFragment : NavHostFragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var rootPOV: View

    override val navViewId = R.id.overlayHostFRM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, ProfileViewModelFactory())[ProfileViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_profile, container, false)

        rootPOV.screenFaderPOV.setOnClickListener {
            rootPOV.screenFaderPOV.visibility = View.GONE
            exit()
        }

        rootPOV.showSignedEventsBTN.setOnClickListener {
            rootPOV.screenFaderPOV.visibility = View.VISIBLE
            show(NavigationGraph.MainApp.Profile.SIGNED_EVENT_LIST)
        }

        viewModel.userDetails.observe(this, Observer {
            if(it != null) {
                rootPOV.nameLBL.text = it.name
                Glide.with(this)
                        .load(it.profilePicURL)
                        .apply(RequestOptions().placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.ic_profile_placeholder_160dp)))
                        .into(rootPOV.profilePicIMG)
                Observable.just(it.qrCodeContent.generateQRCode())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            rootPOV.qrCodeIMG.setImageBitmap(it)
                        }
//                rootPOV.qrCodeIMG.setImageBitmap(it.qrCodeContent.generateQRCode())
            }
        })

        return rootPOV
    }

    private fun String.generateQRCode(): Bitmap {
        val bitMatrix = MultiFormatWriter().encode(this, BarcodeFormat.QR_CODE, 400, 400)
        return BarcodeEncoder().createBitmap(bitMatrix)
    }

    override fun exit() {
        super.exit()
        rootPOV.screenFaderPOV.visibility = View.GONE
    }
}