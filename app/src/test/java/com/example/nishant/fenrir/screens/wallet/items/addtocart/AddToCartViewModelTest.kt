package com.example.nishant.fenrir.screens.wallet.items.addtocart

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

class AddToCartViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddToCartViewModel

    @Before
    fun setup() {
        val wRepo = Mockito.mock(WalletRepository::class.java).also {
            Mockito.`when`(it.getItemByIdInStallOfId("SID", "IID"))
                    .thenReturn(Flowable.just(Item("IID", "Brownie", 180, "SID")))
        }

        viewModel = AddToCartViewModel(wRepo, "SID", "IID")
    }

    @Test
    fun test_ItemNameWhenOverlayJustOpened() {
        assertEquals("Brownie", viewModel.itemName.value)
    }

    @Test
    fun test_QuantityWhenOverlayJustOpened() {
        assertEquals(1, viewModel.quantity.value)
    }

    @Test
    fun test_QuantityWhenUserChangedQuantity() {
        viewModel.incrementItemQuantity()
        viewModel.incrementItemQuantity()
        viewModel.decrementItemQuantity()

        assertEquals(2, viewModel.quantity.value)
    }

    @Test
    fun test_TotalAmountWhenOverlayJustOpened() {
        assertEquals("INR 180", viewModel.totalAmount.value)
    }

    @Test
    fun test_TotalAmountWhenUserChangedQuantity() {
        viewModel.incrementItemQuantity()
        viewModel.incrementItemQuantity()
        viewModel.incrementItemQuantity()
        viewModel.decrementItemQuantity()

        assertEquals("INR 540", viewModel.totalAmount.value)
    }

    @Test
    fun test_MessagesWhenUserTriesToSelectZeroItems() {
        viewModel.incrementItemQuantity()
        viewModel.decrementItemQuantity()
        viewModel.decrementItemQuantity()

        assertEquals("You've to select at least one item to add it to the cart.", viewModel.messages.value)
    }
}