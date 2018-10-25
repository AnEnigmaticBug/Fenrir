package com.example.nishant.fenrir.screens.mainapp.more.n2ovoting

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.mainapp.MainAppRepository
import com.example.nishant.fenrir.domain.mainapp.Comedian
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable

class N2OVotingViewModel(private val mRepo: MainAppRepository) : ViewModel() {

    val comedians: LiveData<List<Comedian>> = MutableLiveData()
    val votingStatus: LiveData<VotingStatus> = MutableLiveData()
    val message: LiveData<String> = MutableLiveData()

    private val d1 = CompositeDisposable()
    private val d2 = CompositeDisposable()

    init {
        d1.set(mRepo.isVotingEnabled()
                .subscribe { _votingStatus ->
                    d2.set(mRepo.getAllComedians()
                            .subscribe { _comedians ->
                                when(_comedians.any { it.hasVote }) {
                                    true  -> {
                                        comedians.toMut().postValue(_comedians)
                                        votingStatus.toMut().postValue(VotingStatus.Voted)
                                    }
                                    false -> {
                                        when(_votingStatus) {
                                            true  -> {
                                                comedians.toMut().postValue(_comedians)
                                                votingStatus.toMut().postValue(VotingStatus.Opened)
                                            }
                                            false -> {
                                                comedians.toMut().postValue(listOf())
                                                votingStatus.toMut().postValue(VotingStatus.Closed)
                                            }
                                        }
                                    }
                                }
                            })
                })
    }

    fun voteForComedian(name: String) {
        message.toMut().value = "Recording vote..."
        mRepo.voteForComedian(name).subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
        d2.clear()
    }
}