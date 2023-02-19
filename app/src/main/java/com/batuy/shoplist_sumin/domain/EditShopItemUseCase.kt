package com.batuy.shoplist_sumin.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
   suspend fun editShopItem(shopItem: ShopItem){
        shopListRepository.editShopItemUseCase(shopItem)

    }
}