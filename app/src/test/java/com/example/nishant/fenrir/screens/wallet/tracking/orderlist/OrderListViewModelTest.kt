package com.example.nishant.fenrir.screens.wallet.tracking.orderlist

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.Item
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

class OrderListViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: OrderListViewModel

    @Before
    fun setup() {
        val items = listOf(
                Item("31", "Brownie", 80, "07"),
                Item("40", "Mc Puff", 40, "07")
        )

        val time1 = LocalTime.of(9,  30)
        val time2 = LocalTime.of(19, 30)

        val wRepo = Mockito.mock(WalletRepository::class.java).also {
            Mockito.`when`(it.getAllTrackedEntries())
                    .thenReturn(Flowable.just(listOf(
                            TrackedEntry("91", "12", items[0], 3, TrackingStatus.Declined, Constants.festDates[0], time1),
                            TrackedEntry("92", "12", items[1], 2, TrackingStatus.Accepted, Constants.festDates[0], time1),
                            TrackedEntry("93", "17", items[1], 1, TrackingStatus.Declined, Constants.festDates[1], time2),
                            TrackedEntry("94", "21", items[0], 3, TrackingStatus.Accepted, Constants.festDates[2], time1)
                    )))
        }

        viewModel = OrderListViewModel(wRepo)
    }

    @Test
    fun test_Orders() {
        val expected = listOf(
                RawOrder("21", "Order Number 3", "Ordered on 29-10-18 at 09:30 AM"),
                RawOrder("17", "Order Number 2", "Ordered on 28-10-18 at 07:30 PM"),
                RawOrder("12", "Order Number 1", "Ordered on 27-10-18 at 09:30 AM")
        )
        assertEquals(expected, viewModel.orders.value)
    }
}