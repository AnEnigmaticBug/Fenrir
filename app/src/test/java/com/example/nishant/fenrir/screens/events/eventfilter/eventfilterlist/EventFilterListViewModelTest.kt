package com.example.nishant.fenrir.screens.events.eventfilter.eventfilterlist

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nishant.fenrir.data.CentralRepository
import com.example.nishant.fenrir.domain.Category
import com.example.nishant.fenrir.domain.EventFilter
import com.example.nishant.fenrir.domain.UserPreferences
import com.example.nishant.fenrir.domain.Venue
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class EventFilterListViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: EventFilterListViewModel

    @Before
    fun setup() {
        val cRepo = Mockito.mock(CentralRepository::class.java)
        Mockito.`when`(cRepo.getUserPreferences())
                .thenReturn(Flowable.just(UserPreferences(EventFilter(Category.Films, Venue.Audi, false, false))))
        viewModel = EventFilterListViewModel(cRepo, FilterType.Venue)
    }

    @Test
    fun test_Items_NoItemSelected() {
        val items = listOf(
                RawListItem(-1, "All", false),
                RawListItem(0, "Audi", true),
                RawListItem(1, "M Lawns", false),
                RawListItem(2, "Rotunda", false),
                RawListItem(3, "Stalls", false),
                RawListItem(4, "SAC Hall", false)
        )
        assertEquals(items, viewModel.items.value)
    }
}