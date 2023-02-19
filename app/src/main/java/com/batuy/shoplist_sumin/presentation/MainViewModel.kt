package com.batuy.shoplist_sumin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batuy.shoplist_sumin.domain.DeleteShopItemUseCase
import com.batuy.shoplist_sumin.domain.EditShopItemUseCase
import com.batuy.shoplist_sumin.domain.GetShopListUseCase
import com.batuy.shoplist_sumin.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {


    val shopList = getShopListUseCase.getShopList()


    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }


    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(newItem)
        }

    }

}