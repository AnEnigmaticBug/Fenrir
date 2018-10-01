package com.example.nishant.fenrir.screens.wallet.tracking.trackorder

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.Item
import com.example.nishant.fenrir.domain.wallet.Stall
import com.example.nishant.fenrir.domain.wallet.TrackedEntry
import com.example.nishant.fenrir.domain.wallet.TrackingStatus
import com.example.nishant.fenrir.util.Constants
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.threeten.bp.LocalTime

class TrackOrderViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TrackOrderViewModel

    @Before
    fun setup() {
        val items = listOf(
                Item("I1", "Mc Veggie", 45, "S1"),
                Item("I3", "French Fries", 30, "S1"),
                Item("I2", "Brownie", 80, "S2"),
                Item("I4", "Margarita", 70, "S3")
        )

        val date1 = Constants.festDates[0]
        val date2 = Constants.festDates[1]

        val time1 = LocalTime.of(9, 20)
        val time2 = LocalTime.of(18, 0)

        val wRepo = Mockito.mock(WalletRepository::class.java).also {
            Mockito.`when`(it.getAllStalls())
                    .thenReturn(Flowable.just(listOf(
                            Stall("S1", "Mc Donalds", "Lite"),
                            Stall("S2", "Goosebumps", "Lite")
                    )))
            Mockito.`when`(it.getAllTrackedEntries())
                    .thenReturn(Flowable.just(listOf(
                            TrackedEntry("T1", "O1", items[3], 2, TrackingStatus.Declined, date1, time1),
                            TrackedEntry("T2", "O2", items[0], 3, TrackingStatus.Accepted, date2, time2),
                            TrackedEntry("T3", "O2", items[1], 1, TrackingStatus.Accepted, date2, time2),
                            TrackedEntry("T4", "O2", items[2], 2, TrackingStatus.Declined, date2, time2)
                    )))
        }

        viewModel = TrackOrderViewModel(wRepo, "O2")
    }

    @Test
    fun test_TrackedOrders() {
        val expected = listOf(
                RawTrackedEntry("T2", "Mc Veggie", "Mc Donalds", "INR 45 x3", TrackingStatus.Accepted),
                RawTrackedEntry("T3", "French Fries", "Mc Donalds", "INR 30 x1", TrackingStatus.Accepted),
                RawTrackedEntry("T4", "Brownie", "Goosebumps", "INR 80 x2", TrackingStatus.Declined)
        )
        assertEquals(expected, viewModel.trackedEntries.value)
    }
}