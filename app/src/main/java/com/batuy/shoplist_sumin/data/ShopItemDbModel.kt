package com.batuy.shoplist_sumin.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "shop_items")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val count: String,
    val enabled: Boolean

)