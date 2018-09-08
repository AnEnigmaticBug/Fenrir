package com.example.nishant.fenrir.eventlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.CentralRepository
import com.example.nishant.fenrir.data.EventRepository
import com.example.nishant.fenrir.util.Constants
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.threeten.bp.LocalDate

class EventListViewModel(cRepo: CentralRepository, eRepo: EventRepository) : ViewModel() {

    val rawEvents: LiveData<List<RawEvent>> = MutableLiveData()
    val dateTitle: LiveData<String> = MutableLiveData()

    private val dateSubject = BehaviorSubject.create<Int>()

    private var d1 = CompositeDisposable()
    private var d2 = CompositeDisposable()
    private var d3 = CompositeDisposable()

    init {
        // Display the events occurring today by default.
        when(LocalDate.now().dayOfMonth) {
            Constants.festDates[0].dayOfMonth -> changeDateToIndex(0)
            Constants.festDates[1].dayOfMonth -> changeDateToIndex(1)
            Constants.festDates[2].dayOfMonth -> changeDateToIndex(2)
            Constants.festDates[3].dayOfMonth -> changeDateToIndex(3)
            Constants.festDates[4].dayOfMonth -> changeDateToIndex(4)
            else                              -> changeDateToIndex(0)
        }

        // Retrieve the event data from the repositories.
        d1.set(cRepo.getUserPreferences()
                .subscribe { _prefs ->
                    d2.set(dateSubject
                            .subscribe { _dateIndex ->
                                d3.set(eRepo.getAllEvents()
                                        .subscribe { _events ->
                                            rawEvents.toMut().postValue(
                                                    _events
                                                            .filter { _prefs.eventFilter.isSatisfiedByEvent(it) && it.date == Constants.festDates[_dateIndex] }
                                                            .map { RawEvent.fromEvent(it) }
                                            )
                                        })
                            })
                })
    }

    fun changeDateToIndex(i: Int) {
        dateSubject.onNext(i)
        dateTitle.toMut().value = when(i) {
            0    -> Constants.festDates[0].prettyString()
            1    -> Constants.festDates[1].prettyString()
            2    -> Constants.festDates[2].prettyString()
            3    -> Constants.festDates[3].prettyString()
            4    -> Constants.festDates[4].prettyString()
            else -> throw IllegalArgumentException("No FestDate having index $i")
        }
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
        d2.clear()
        d3.clear()
    }
}