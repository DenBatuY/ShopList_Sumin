package com.batuy.shoplist_sumin.data

import com.batuy.shoplist_sumin.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {

    fun mapEntityToDbModel(shopItem: ShopItem): ShopItemDbModel {
        return ShopItemDbModel(
            id = shopItem.id,
            name = shopItem.name,
            count = shopItem.count,
            enabled = shopItem.enabled
        )
    }

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel): ShopItem {
        return ShopItem(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name,
            count = shopItemDbModel.count,
            enabled = shopItemDbModel.enabled
        )
    }

    fun mapListDbModelToListEntity(listDbModel: List<ShopItemDbModel>) = listDbModel.map {
        mapDbModelToEntity(it)
    }
}