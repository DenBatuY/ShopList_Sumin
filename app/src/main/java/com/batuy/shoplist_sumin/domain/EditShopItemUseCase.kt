package com.batuy.shoplist_sumin.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItemUseCase(shopItem: ShopItem){
        shopListRepository.editShopItemUseCase(shopItem)

    }
}