package com.batuy.shoplist_sumin.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batuy.shoplist_sumin.data.ShopListRepositoryImpl
import com.batuy.shoplist_sumin.domain.DeleteShopItemUseCase
import com.batuy.shoplist_sumin.domain.EditShopItemUseCase
import com.batuy.shoplist_sumin.domain.GetShopListUseCase
import com.batuy.shoplist_sumin.domain.ShopItem

class MainViewModel:ViewModel() {

    private val repository = ShopListRepositoryImpl


    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = MutableLiveData<List<ShopItem>>()

    fun getShopList(){
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
        getShopList()
    }


    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
        getShopList()
    }
}