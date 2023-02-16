package com.batuy.shoplist_sumin.domain

class DeleteShopItemUseCase(private  val shopListRepository: ShopListRepository) {

   suspend fun deleteShopItem(shopItem: ShopItem){
        shopListRepository.deleteShopItem(shopItem)

    }
}