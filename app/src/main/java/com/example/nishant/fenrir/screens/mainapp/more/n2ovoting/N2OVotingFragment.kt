package com.example.nishant.fenrir.screens.mainapp.more.n2ovoting

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nishant.fenrir.R
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_n2o_voting.view.*
import java.util.concurrent.TimeUnit

class N2OVotingFragment : Fragment(), ComediansAdapter.ClickListener {

    private lateinit var viewModel: N2OVotingViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, N2OVotingViewModelFactory())[N2OVotingViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_n2o_voting, container, false)

        rootPOV.comediansRCY.adapter = ComediansAdapter(this)

        viewModel.votingStatus.observe(this, Observer {
            when(it!!) {
                VotingStatus.Closed -> {
                    rootPOV.comediansRCY.visibility = View.INVISIBLE
                    rootPOV.messageLBL.text = "Voting is not ongoing"
                    rootPOV.messageLBL.visibility = View.VISIBLE
                    rootPOV.warningLBL.visibility = View.INVISIBLE
                }
                VotingStatus.Opened -> {
                    rootPOV.comediansRCY.visibility = View.VISIBLE
                    rootPOV.messageLBL.visibility = View.INVISIBLE
                    rootPOV.warningLBL.visibility = View.VISIBLE
                }
                VotingStatus.Voted  -> {
                    Flowable.timer(2, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                rootPOV.comediansRCY.visibility = View.INVISIBLE
                                rootPOV.messageLBL.text = "Thank you for voting"
                                rootPOV.messageLBL.visibility = View.VISIBLE
                                rootPOV.warningLBL.visibility = View.INVISIBLE
                            }
                }
            }
        })

        viewModel.comedians.observe(this, Observer {
            (rootPOV.comediansRCY.adapter as ComediansAdapter).comedians = it?: listOf()
        })

        viewModel.message.observe(this, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        return rootPOV
    }

    override fun onVoteBTNPressed(name: String) {
        when(viewModel.votingStatus.value) {
            VotingStatus.Opened -> viewModel.voteForComedian(name)
            else                -> Toast.makeText(requireContext(), "Voting hasn't begun", Toast.LENGTH_SHORT).show()
        }
    }
}