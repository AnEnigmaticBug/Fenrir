package com.example.nishant.fenrir.screens.wallet.cart

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.CartEntry
import com.example.nishant.fenrir.domain.wallet.Item
import com.example.nishant.fenrir.domain.wallet.Stall
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class CartViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel

    @Test
    fun test_RawOrders() {
        val wRepo = Mockito.mock(WalletRepository::class.java).also {
            Mockito.`when`(it.getAllEntriesInCart())
                    .thenReturn(Flowable.just(listOf(
                            CartEntry("13", Item("21", "Mc Veggie", 30, "22"), 2, true),
                            CartEntry("67", Item("03", "Garlic Bread", 140, "63"), 4, true),
                            CartEntry("32", Item("14", "Mineral Water", 110, "73"), 5, false)
                    )))
            Mockito.`when`(it.getAllStalls())
                    .thenReturn(Flowable.just(listOf(
                            Stall("63", "Redi", "Rehdi"),
                            Stall("22", "Looters", "Looto"),
                            Stall("73", "ANC", "Paneer Roll")
                    )))
        }

        viewModel = CartViewModel(wRepo)

        val expected = listOf(
                RawCartEntry("13", "Mc Veggie", "Looters", "INR 30 x2", true),
                RawCartEntry("67", "Garlic Bread", "Redi", "INR 140 x4", true),
                RawCartEntry("32", "Mineral Water", "ANC", "INR 110 x5", false)
        )
        assertEquals(expected, viewModel.rawOrders.value)
    }

    @Test
    fun test_BuyStatus_WhenNoOrderPlaced() {
        val wRepo = Mockito.mock(WalletRepository::class.java).also {
            Mockito.`when`(it.getAllEntriesInCart())
                    .thenReturn(Flowable.just(listOf(
                            CartEntry("13", Item("21", "Mc Veggie", 30, "22"), 2, true),
                            CartEntry("67", Item("03", "Garlic Bread", 140, "63"), 4, true),
                            CartEntry("32", Item("14", "Mineral Water", 110, "73"), 5, false)
                    )))
            Mockito.`when`(it.getAllStalls())
                    .thenReturn(Flowable.just(listOf(
                            Stall("63", "Redi", "Rehdi"),
                            Stall("22", "Looters", "Looto"),
                            Stall("73", "ANC", "Paneer Roll")
                    )))
        }

        val viewModel = CartViewModel(wRepo)

        assertEquals(BuyAttemptStatus.Idle, viewModel.buyStatus.value)
    }

    @Test
    fun test_TotalCost() {
        val wRepo = Mockito.mock(WalletRepository::class.java).also {
            Mockito.`when`(it.getAllEntriesInCart())
                    .thenReturn(Flowable.just(listOf(
                            CartEntry("13", Item("21", "Mc Veggie", 30, "22"), 2, true),
                            CartEntry("67", Item("03", "Garlic Bread", 140, "63"), 4, true),
                            CartEntry("32", Item("14", "Mineral Water", 110, "73"), 5, false)
                    )))
            Mockito.`when`(it.getAllStalls())
                    .thenReturn(Flowable.just(listOf(
                            Stall("63", "Redi", "Rehdi"),
                            Stall("22", "Looters", "Looto"),
                            Stall("73", "ANC", "Paneer Roll")
                    )))
        }

        viewModel = CartViewModel(wRepo)

        assertEquals("Total Price: INR ${30*2 + 140*4 + 110*5}", viewModel.totalCost.value)
    }
}