package com.example.nishant.fenrir.screens.wallet.stalls

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.Stall
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class StallsViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun test_RawStalls() {
        val walletRepository = Mockito.mock(WalletRepository::class.java).also {
            Mockito.`when`(it.getAllStalls())
                    .thenReturn(Flowable.just(listOf(
                            Stall("13", "Goo13", "13 Brownies"),
                            Stall("14", "Goo14", "14 Brownies"),
                            Stall("15", "Goo15", "15 Brownies"),
                            Stall("16", "Goo16", "16 Brownies"),
                            Stall("17", "Goo17", "17 Brownies"),
                            Stall("18", "Goo18", "18 Brownies")
                    )))
        }

        val viewModel = StallsViewModel(walletRepository)

        val expected = listOf(
                RawStall("13", "Goo13"),
                RawStall("14", "Goo14"),
                RawStall("15", "Goo15"),
                RawStall("16", "Goo16"),
                RawStall("17", "Goo17"),
                RawStall("18", "Goo18")
        )

        assertEquals(expected, viewModel.rawStalls.value)
    }
}