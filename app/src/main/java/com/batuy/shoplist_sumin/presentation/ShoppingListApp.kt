package com.batuy.shoplist_sumin.presentation

import android.app.Application
import com.batuy.shoplist_sumin.di.DaggerApplicationComponent

class ShoppingListApp:Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}