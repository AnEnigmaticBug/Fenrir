package com.example.nishant.fenrir.screens.wallet.money.addmoney.outstee

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavigationHost
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_add_money_outstee.view.*

class AddMoneyOutsteeFragment : Fragment() {

    private lateinit var navigationHost: NavigationHost
    private lateinit var viewModel: AddMoneyOutsteeViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navigationHost = parentFragment as NavigationHost

        viewModel = ViewModelProviders.of(this, AddMoneyOutsteeViewModelFactory())[AddMoneyOutsteeViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_add_money_outstee, container, false)

        rootPOV.closeBTN.setOnClickListener {
            navigationHost.exit()
        }

        viewModel.qrCodeContent.observe(this, Observer {
            Observable.just(it!!.generateQRCode())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        rootPOV.qrCodeIMG.setImageBitmap(it)
                    }
        })

        return rootPOV
    }

    private fun String.generateQRCode(): Bitmap {
        val bitMatrix = MultiFormatWriter().encode(this, BarcodeFormat.QR_CODE, 400, 400)
        return BarcodeEncoder().createBitmap(bitMatrix)
    }
}