package com.example.nishant.fenrir.screens.events.eventfilter.eventfilterlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.CentralRepository
import com.example.nishant.fenrir.domain.Category
import com.example.nishant.fenrir.domain.Venue
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable

class EventFilterListViewModel(private val cRepo: CentralRepository, private val type: FilterType) : ViewModel() {

    val items: LiveData<List<RawListItem>> = MutableLiveData()


    private val allInclusiveItemIndex = -1


    private val d1 = CompositeDisposable()
    private val d2 = CompositeDisposable()

    init {
        d1.set(cRepo.getUserPreferences()
                .subscribe { _prefs ->
                    val its = ArrayList<RawListItem>()
                    when(type) {
                        FilterType.Category -> {
                            its.add(when(_prefs.eventFilter.category) {
                                null -> RawListItem(allInclusiveItemIndex, "All", true )
                                else -> RawListItem(allInclusiveItemIndex, "All", false)
                            })

                            Category.values().forEachIndexed { i, c ->
                                its.add(when(_prefs.eventFilter.category) {
                                    c    -> RawListItem(i, c.prettyString(), true )
                                    else -> RawListItem(i, c.prettyString(), false)
                                })
                            }
                        }
                        FilterType.Venue    -> {
                            its.add(when(_prefs.eventFilter.venue) {
                                null -> RawListItem(allInclusiveItemIndex, "All", true )
                                else -> RawListItem(allInclusiveItemIndex, "All", false)
                            })

                            Venue.values().forEachIndexed { i, v ->
                                its.add(when(_prefs.eventFilter.venue) {
                                    v    -> RawListItem(i, v.prettyString(), true )
                                    else -> RawListItem(i, v.prettyString(), false)
                                })
                            }
                        }
                    }

                    items.toMut().postValue(its)
                })
    }

    fun onItemSelectedWithIndex(i: Int) {
        d2.set(cRepo.getUserPreferences()
                .take(1)
                .subscribe { _prefs ->
                    when(type) {
                        FilterType.Category -> {
                            val category = when(i) {
                                allInclusiveItemIndex -> null
                                else                  -> Category.values()[i]
                            }
                            cRepo.setUserPreferences(_prefs.copy(eventFilter = _prefs.eventFilter.copy(category = category))).subscribe()
                        }
                        FilterType.Venue    -> {
                            val venue = when(i) {
                                allInclusiveItemIndex -> null
                                else                  -> Venue.values()[i]
                            }
                            cRepo.setUserPreferences(_prefs.copy(eventFilter = _prefs.eventFilter.copy(venue = venue))).subscribe()
                        }
                    }
                })
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
        d2.clear()
    }
}