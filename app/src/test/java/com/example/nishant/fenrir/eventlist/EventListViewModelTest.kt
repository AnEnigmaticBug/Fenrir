package com.example.nishant.fenrir.eventlist

import com.example.nishant.fenrir.data.CentralRepository
import com.example.nishant.fenrir.data.EventRepository
import com.example.nishant.fenrir.domain.*
import com.example.nishant.fenrir.util.Constants
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.threeten.bp.LocalTime
import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.rules.TestRule
import org.junit.Rule



class EventListViewModelTest {

    @Rule @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private val events = listOf(
            Event("10", "E10", "A10", "R10", Venue.MLawns, Category.Drama, Constants.festDates[0], LocalTime.of(22, 30), 60, false),
            Event("10", "E10", "A10", "R10", Venue.Stalls, Category.Music, Constants.festDates[0], LocalTime.of(21, 0 ), 90, false),
            Event("10", "E10", "A10", "R10", Venue.MLawns, Category.Drama, Constants.festDates[0], LocalTime.of(13, 30), 40, true ),
            Event("10", "E10", "A10", "R10", Venue.Stalls, Category.Drama, Constants.festDates[1], LocalTime.of(10, 45), 30, false),
            Event("10", "E10", "A10", "R10", Venue.MLawns, Category.Music, Constants.festDates[1], LocalTime.of(20, 0 ), 90, false),
            Event("10", "E10", "A10", "R10", Venue.Stalls, Category.Films, Constants.festDates[2], LocalTime.of(23, 15), 60, false)
    )

    private lateinit var viewModel: EventListViewModel

    @Before
    fun setup() {
        val cRepo = Mockito.mock(CentralRepository::class.java)
        Mockito.`when`(cRepo.getUserPreferences())
                .thenReturn(Flowable.just(UserPreferences(EventFilter(Category.Drama, null, false, false))))

        val eRepo = Mockito.mock(EventRepository::class.java)
        Mockito.`when`(eRepo.getAllEvents())
                .thenReturn(Flowable.just(events))

        viewModel = EventListViewModel(cRepo, eRepo)
    }

    @Test
    fun test_RawEvents_WhenNoDateSelected() {
        val rawEvents = listOf(
                RawEvent.fromEvent(events[0]),
                RawEvent.fromEvent(events[2])
        )
        assertEquals(rawEvents, viewModel.rawEvents.value)
    }

    @Test
    fun test_RawEvents_When2DatesSelected() {
        viewModel.changeDateToIndex(2)
        viewModel.changeDateToIndex(1)
        val rawEvents = listOf(
                RawEvent.fromEvent(events[3])
        )
        assertEquals(rawEvents, viewModel.rawEvents.value)
    }

    @Test
    fun test_DateTitle_WhenNoDateSelected() {
        assertEquals("October 27", viewModel.dateTitle.value)
    }

    @Test
    fun test_DateTitle_When2DatesSelected() {
        viewModel.changeDateToIndex(2)
        viewModel.changeDateToIndex(1)
        assertEquals("October 28", viewModel.dateTitle.value)
    }
}