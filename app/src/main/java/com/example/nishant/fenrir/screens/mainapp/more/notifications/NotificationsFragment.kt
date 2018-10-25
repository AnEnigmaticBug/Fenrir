package com.example.nishant.fenrir.screens.mainapp.more.notifications

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_notifications.view.*

class NotificationsFragment : Fragment() {

    private lateinit var viewModel: NotificationsViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, NotificationsViewModelFactory())[NotificationsViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_notifications, container, false)

        rootPOV.notificationsRCY.adapter = NotificationsAdapter()

        viewModel.payloadedNotifications.observe(this, Observer {
            (rootPOV.notificationsRCY.adapter as NotificationsAdapter).notifications = it?: listOf()
        })

        return rootPOV
    }
}