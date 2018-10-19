package com.example.nishant.fenrir.screens.wallet.money.sendmoney

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.example.nishant.fenrir.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fra_send_money.view.*

class SendMoneyFragment : Fragment() {

    private lateinit var viewModel: SendMoneyViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, SendMoneyViewModelFactory())[SendMoneyViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_send_money, container, false)

        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            setupScanning()
        }
        else {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 78)
        }

        viewModel.transferStatus.observe(this, Observer {
            when(it!!) {
                is TransferMoneyAttemptStatus.Success -> Toast.makeText(requireContext(), "Transfer successful", Toast.LENGTH_SHORT).show()
                is TransferMoneyAttemptStatus.Failure -> Toast.makeText(requireContext(), (it as TransferMoneyAttemptStatus.Failure).message, Toast.LENGTH_SHORT).show()
            }
            findNavController().navigateUp()
        })

        return rootPOV
    }

    fun setupScanning() {
        val scanner = CodeScanner(requireContext(), rootPOV.scannerCSV).also { it.startPreview() }
        scanner.setDecodeCallback { result ->
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        AlertDialog.Builder(requireContext())
                                .setMessage("Are you sure you want to send ${rootPOV.amountLBL.text}?")
                                .setPositiveButton("Yes") { _, _ ->
                                    when(rootPOV.amountLBL.text.toString()) {
                                        ""   -> {
                                            Toast.makeText(requireContext(), "Please enter a number", Toast.LENGTH_SHORT).show()
                                            scanner.startPreview()
                                        }
                                        else -> {
                                            viewModel.transferMoney(result.text, rootPOV.amountLBL.text.toString().toInt())
                                        }
                                    }
                                }
                                .setNegativeButton("No") { _, _ ->
                                    Toast.makeText(requireContext(), "Transfer cancelled", Toast.LENGTH_SHORT).show()
                                    findNavController().navigateUp()
                                }
                                .show()
                    }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 78) {
            if(permissions[0] == android.Manifest.permission.CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupScanning()
                return
            }
        }
        Toast.makeText(requireContext(), "Camera access is needed for transferring money", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }
}