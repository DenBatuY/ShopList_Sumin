package com.batuy.shoplist_sumin.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

   suspend fun addShopItem(shopItem: ShopItem)

   suspend fun deleteShopItem(shopItem: ShopItem)

   suspend fun editShopItemUseCase(shopItem: ShopItem)

   suspend fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
}