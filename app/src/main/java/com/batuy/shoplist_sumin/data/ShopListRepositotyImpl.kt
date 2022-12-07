package com.batuy.shoplist_sumin.data

import com.batuy.shoplist_sumin.domain.ShopItem
import com.batuy.shoplist_sumin.domain.ShopListRepository

object ShopListRepositotyImpl:ShopListRepository {

    private val shopList= mutableListOf<ShopItem>()
    private var autoIncrimentId=0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id==ShopItem.UNDEFINED_ID){
        shopItem.id= autoIncrimentId++}
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItemUseCase(shopItem: ShopItem) {
        val oldElement= getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId:Int): ShopItem {
        return shopList.find { it.id== shopItemId}?:
               throw RuntimeException("Element with Id$shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}