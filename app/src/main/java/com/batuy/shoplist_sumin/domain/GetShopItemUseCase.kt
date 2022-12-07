package com.batuy.shoplist_sumin.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem():ShopItem{
       return shopListRepository.getShopItem()

    }
}