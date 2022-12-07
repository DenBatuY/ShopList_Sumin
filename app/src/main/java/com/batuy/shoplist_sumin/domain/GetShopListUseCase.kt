package com.batuy.shoplist_sumin.domain

class GetShopListUseCase(private val shopListRepository: ShopListRepository){
   fun getShopList():List<ShopItem>{
      return shopListRepository.getShopList()
   }
}