package com.batuy.shoplist_sumin.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItem(shopItem: ShopItem){
        shopListRepository.editShopItemUseCase(shopItem)

    }
}