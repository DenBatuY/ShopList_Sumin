package com.batuy.shoplist_sumin.domain

data class ShopItem(

    val name:String,
    val count:String,
    val enabled:Boolean,
    var id:Int=UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID =-1
    }
}
