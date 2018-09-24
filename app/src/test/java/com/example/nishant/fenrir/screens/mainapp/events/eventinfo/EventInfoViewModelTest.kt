package com.example.nishant.fenrir.screens.mainapp.events.eventinfo

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nishant.fenrir.data.repository.mainapp.EventRepository
import com.example.nishant.fenrir.domain.mainapp.Category
import com.example.nishant.fenrir.domain.mainapp.Event
import com.example.nishant.fenrir.domain.mainapp.Venue
import com.example.nishant.fenrir.util.Constants
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.threeten.bp.LocalTime

class EventInfoViewModelTest {

    @Rule @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: EventInfoViewModel
    private val eventId = "XX"

    @Before
    fun setup() {
        val eRepo = Mockito.mock(EventRepository::class.java)
        Mockito.`when`(eRepo.getEventById(eventId))
                .thenReturn(Flowable.just(Event(eventId, "EX", "AX", "RX", Venue.Audi, Category.Films, Constants.festDates[2], LocalTime.of(22, 30), 45, false)))
        viewModel = EventInfoViewModel(eRepo, eventId)
    }

    @Test
    fun test_RawEvent_WhenNotToggledMainContent() {
        assertEquals(RawEvent(eventId, "EX", "Audi", "10:30 PM, Day 3", "AX"), viewModel.rawEvent.value)
    }

    @Test
    fun test_RawEvent_WhenHasToggledMainContent() {
        viewModel.toggleMainContents()
        assertEquals(RawEvent(eventId, "EX", "Audi", "10:30 PM, Day 3", "RX"), viewModel.rawEvent.value)
    }
}