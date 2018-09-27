package com.example.nishant.fenrir.screens.wallet.items

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.Item
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class ItemsViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private val stallId = "S9"
    private lateinit var viewModel: ItemsViewModel

    @Before
    fun setup() {
        val walletRepository = Mockito.mock(WalletRepository::class.java).also {
            Mockito.`when`(it.getAllItemsInStallOfId(stallId))
                    .thenReturn(Flowable.just(listOf(
                            Item("71", "Pencil Packet", 15, stallId),
                            Item("72", "Physics Lab Manual", 100, stallId)
                    )))
        }
        viewModel = ItemsViewModel(walletRepository, stallId)
    }

    @Test
    fun test_RawItems() {
        val expected = listOf(
                RawItem("71", "Pencil Packet", "INR 15"),
                RawItem("72", "Physics Lab Manual", "INR 100")
        )

        assertEquals(expected, viewModel.rawItems.value)
    }
}