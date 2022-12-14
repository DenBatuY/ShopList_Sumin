package com.batuy.shoplist_sumin.domain

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItemUseCase(shopItem: ShopItem)

    fun getShopItem(ShopItemId:Int):ShopItem

    fun getShopList():List<ShopItem>
}